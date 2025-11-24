output "public_ip" {
  value = aws_instance.quote_api.public_ip
}

output "app_url" {
  value = "http://${aws_instance.quote_api.public_ip}:8080/api/quote"
}
