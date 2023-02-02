## ---------------------
## Provider configuration
## ---------------------
variable "project_id" {
  type        = string
  description = "Project ID in GCP"
}
variable "region" {
  type        = string
  description = "Region in which to manage GCP resources"
}
variable "zone" {
  type        = string
  description = "zone in which to manage GCP resources"
}

## ---------------------
## Backend configuration
## ---------------------
variable "bucket_name" {
  type        = string
  description = "backend bucket"
}
variable "tfstate_prefix" {
  type        = string
  description = "backend bucket prefix"
}

## ---------------------
## VPC configuration
## ---------------------
variable "routing_mode" {
  type        = string
  description = "VPC Routing mode"
  default     = "REGIONAL"
}

## ---------------------
## Subnet configuration
## ---------------------
variable "subnet_name" {
  type        = string
  description = "The name of the Subnet"
}

variable "ip_cidr_range" {
  type        = string
  description = "Subnet CIDR IP Range"
  default     = "10.204.0.0/24"
}

variable "fw_name" {
  type        = string
  description = "The name of the Firewall"
  default     = "allow-ssh"
}

variable "pod_subnet_name" {
  type = string
}

variable "pod_subnet_cidr" {
  type = string
}

variable "service_subnet_name" {
  type = string
}

variable "service_subnet_cidr" {
  type = string
}

## ---------------------
## Cluster configuration
## ---------------------
variable "name" {
  type        = string
  description = "The name of the cluster, unique within the project and zone"
}

variable "ntw_name" {
  type        = string
  description = "The name of the network to create to run cluster instances"
}

variable "description" {
  type        = string
  description = "Cluster description"
}

variable "location" {
  type        = string
  description = "The location (zone/region) the master and nodes specified in initial_node_count should be created in"
  default     = "asia-northeast3-a"
}

variable "initial_node_count" {
  type        = number
  description = "Number of nodes in the cluster"
  default     = 1
}

## ---------------------
## Node Pool configuration
## ---------------------
variable "np_name" {
  type        = string
  description = "The name of the Node Pool"
}

variable "node_count" {
  type        = number
  description = "The number of nodes to create in this Node Pool"
  default     = 1
}
