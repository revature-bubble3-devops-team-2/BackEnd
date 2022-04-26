variable "availability_zones" {
  type  = list(string)
  default = ["eu-west-1a", "eu-west-1b"]
  description = "List of availability zones for the selected region"
}

variable "region" {
  description = "aws region to deploy to"
  type        = string
}

   

