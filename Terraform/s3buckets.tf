module "s3" {
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