terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.11.0"
    }
    kubernetes = {
      version = "~> 2.10.0"
    }
    helm = {
      version = "~> 2.5.1"
    }
    random = {
      source  = "hashicorp/random"
      version = "3.1.0"
    }

    local = {
      source  = "hashicorp/local"
      version = "2.1.0"
    }

    null = {
      source  = "hashicorp/null"
      version = "3.1.0"
    }
  }
  required_version = ">= 0.14.9"
}

data "aws_availability_zones" "available" {}

provider "aws" {
  region = var.region
}

locals {
  cluster_name    = "team-magma-${random_string.suffix.result}"
  cluster_version = "1.22"
}

resource "random_string" "suffix" {
  length  = 8
  special = false
}

resource "aws_iam_user_policy" "maxie-policy" {
  name = "220307-kevin-sre-team-magma-policy"
  user = aws_iam_user.maxie.name
  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = [
          "eks:DescribeCluster",
          "eks:AccessKubernetesApi"
        ]
        Effect   = "Allow"
        Resource = module.eks.cluster_arn
      },
    ]
  })
}

resource "aws_iam_user" "maxie" {
  name = "220307-kevin-sre-team-magma"
  path = "/"

  tags = {
    team           = "magma"
    "Created By"   = "Terraform"
    "Creating On"  = "04-28-22"
    "Delete After" = "05-16-22"
  }
}

resource "aws_iam_access_key" "maxie" {
  user = aws_iam_user.maxie.name
}
