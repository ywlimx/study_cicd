project_id = "_PROJECT_"
region     = "asia-northeast3"
zone       = "asia-northeast3-a"
##############
#VPC Vars
##############
ntw_name            = "cicd-edu-cluster-network-feature" # VPC network name which will be created
routing_mode        = "REGIONAL"
subnet_name         = "cicd-edu-cluster-subnet-feature"
ip_cidr_range       = "10.204.2.0/24"
fw_name             = "allow-ssh-feature"
pod_subnet_name     = "gke-cicd-edu-cluster-pods-subnet-feature"
pod_subnet_cidr     = "10.232.0.0/14"
service_subnet_name = "gke-cicd-edu-cluster-services-subnet-feature"
service_subnet_cidr = "10.236.0.0/20"

##############
#Common Vars
##############
location = "asia-northeast3-a"

##############
#Cluster Vars
##############
name               = "cicd-edu-cluster-_BRANCH_NAME_" # The name of the cluster
description        = "Test cluster for cicd-edu-feature"
initial_node_count = 1 # number of nodes in the cluster

##############
#Node Pool Vars
##############
np_name    = "cicd-edu-ndpl-feature"
node_count = 1

##############
#Terraform State file configuration
##############
bucket_name    = "_PROJECT_-bucket"
tfstate_prefix = "feature.terraform.tfstate"
