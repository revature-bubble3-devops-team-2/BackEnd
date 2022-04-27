module "eks" {
  source          = "terraform-aws-modules/eks/aws"
  version         = "18.20.5"
  cluster_name    = local.cluster_name
  cluster_version = local.cluster_version

  vpc_id = module.vpc.vpc_id
  subnet_ids = module.vpc.private_subnets

  self_managed_node_group_defaults = {
    instance_type = "t2.medium"
    update_launch_template_default_version = true
  }

  self_managed_node_groups = {
    magma = {
      name = "magma"
      public_ip = true
      max_size = 5
      desired_size = 3
    }
  }

  manage_aws_auth_configmap = true

  aws_auth_roles = [
    {
      rolearn  = "arn:aws:iam::${var.iam_id}:role/admin"
      username = "admin"
      groups   = ["system:masters"]
    },
  ]

  aws_auth_users = [
    {
      userarn  = "arn:aws:iam::${var.iam_id}:user/maxie"
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
