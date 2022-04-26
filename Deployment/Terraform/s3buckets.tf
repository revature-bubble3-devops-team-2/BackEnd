module "s3" {
  source = "terraform-aws-modules/s3-bucket/aws"
  resource "aws_s3_bucket" "onebucket" {
   bucket = "magma-bucket"
   acl = "private"
   versioning {
      enabled = true
   }
   tags = {
     Name = "magma-bucket"
     Environment = "Prod"
   }
}

}