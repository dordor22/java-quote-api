variable "aws_region" {
  type    = string
  default = "us-east-1"
}

variable "instance_type" {
  type    = string
  default = "t3.micro"
}

variable "docker_image" {
  type        = string
  description = "Docker image to run on EC2"
}

variable "keypair_name" {
  type        = string
  description = "Existing AWS key pair name for SSH"
}

variable "ssh_cidr" {
  type        = string
  description = "Your public IP with /32, to allow SSH access"
}
