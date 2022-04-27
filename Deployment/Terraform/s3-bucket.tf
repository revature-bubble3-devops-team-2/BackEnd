resource "aws_s3_bucket" "s3" {
  bucket = "bubble-bucket"

  tags = {
    Name        = "bubble-bucket"
    Environment = "Prod"
  }
}

resource "aws_s3_bucket_acl" "bubble-bucket" {
  bucket = aws_s3_bucket.s3.id
  acl    = "private"
}