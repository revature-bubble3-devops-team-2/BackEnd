resource "aws_s3_bucket" "s3" {
  bucket = "team-magma-bubble-bucket"

  tags = {
    Name        = "team-magma-bubble-bucket"
    Environment = "Prod"
  }
}

resource "aws_s3_bucket_acl" "team-magma-bubble-bucket" {
  bucket = aws_s3_bucket.s3.id
  acl    = "public-read"
}