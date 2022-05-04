module "eks" {
  source          = "terraform-aws-modules/eks/aws"
  version         = "18.20.5"
  cluster_name    = local.cluster_name
  cluster_version = local.cluster_version

  vpc_id     = module.vpc.vpc_id
  subnet_ids = module.vpc.private_subnets

  self_managed_node_group_defaults = {
    instance_type                          = "t2.medium"
    update_launch_template_default_version = true
  }

  self_managed_node_groups = {
    magma = {
      name         = "magma"
      public_ip    = true
      max_size     = 5
      desired_size = 3
    }
  }

  node_security_group_additional_rules = {
    ingress_self_all = {
      description = "Node to node ingress all ports/protocols"
      protocol    = "-1"
      from_port   = 0
      to_port     = 0
      type        = "ingress"
      self        = true
    }

    egress_all = {
      description      = "Node egress to everywhere"
      protocol         = "-1"
      from_port        = 0
      to_port          = 0
      type             = "egress"
      cidr_blocks      = ["0.0.0.0/0"]
      ipv6_cidr_blocks = ["::/0"]
    }
  }

  create_aws_auth_configmap = true
  manage_aws_auth_configmap = true

  aws_auth_users = [
    {
      userarn  = aws_iam_user.maxie.arn
      username = "maxie"
      groups   = ["system:masters"]
    },
  ]

  tags = {
    Environment = "Prod"
    Terraform   = "true"
  }
}

data "aws_eks_cluster" "cluster" {
  name = module.eks.cluster_id
}

data "aws_eks_cluster_auth" "cluster" {
  name = module.eks.cluster_id
}
