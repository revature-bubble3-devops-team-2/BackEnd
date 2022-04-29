variable "availability_zones" {
  type        = list(string)
  default     = ["us-east-1", "us-east-2"]
  description = "List of availability zones for the selected region"
}

variable "region" {
  description = "aws region to deploy to"
  type        = string
  default     = "us-east-1"
}


