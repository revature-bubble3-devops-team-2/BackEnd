output "user_arn" {
  value = aws_iam_user.maxie.arn
}

output "user_access_key" {
  value = aws_iam_access_key.maxie
}
