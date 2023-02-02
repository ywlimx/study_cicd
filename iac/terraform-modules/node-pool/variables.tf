variable "np_name" {
  type        = string
  description = "The name of the Node Pool"
  default     = "cicd-edu-ndpl"
}

variable "location" {
  type        = string
  description = "In which location(region/zone) to create the Node Pool"
}

variable "node_count" {
  type        = number
  description = "The number of nodes to create in this Node Pool"
  default     = 1
}

variable "cluster_name" {
  type        = string
  description = "Name of the cluster to which to add this Node Pool"
}

variable "machine_type" {
  type        = string
  description = "The type of machine to use for nodes in the pool"
  default     = "n1-standard-1"
}

variable "disk_size_gb" {
  type        = number
  description = "Disk of which size to attach to the nodes in the pool"
  default     = 10
}

variable "image_type" {
  type        = string
  description = "The image type to use for nodes. See supported image types https://cloud.google.com/kubernetes-engine/docs/concepts/node-images"
  default     = "cos_containerd" # Container-Optimized OS
}
