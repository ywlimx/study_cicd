DIR=$(echo /var/lib/jenkins/workspace/*_cicd-edu*_master/iac/cicd-edu-cluster)
if [ -d "$DIR" ];then
        cd /var/lib/jenkins/workspace/*_cicd-edu*_master/iac/cicd-edu-cluster
        terraform destroy -var-file="configurations/prod-config.tfvars" -auto-approve
fi
DIR=$(echo /var/lib/jenkins/workspace/*_cicd-edu*_develop/iac/cicd-edu-cluster)
if [ -d "$DIR" ];then
        cd /var/lib/jenkins/workspace/*_cicd-edu*_develop/iac/cicd-edu-cluster
        terraform destroy -var-file="configurations/dev-config.tfvars" -auto-approve
fi
DIR=$(echo /var/lib/jenkins/workspace/*_cicd-edu*_feature*/iac/cicd-edu-cluster)
if [ -d "$DIR" ];then
        cd /var/lib/jenkins/workspace/*_cicd-edu*_feature*/iac/cicd-edu-cluster
        terraform destroy -var-file="configurations/feature-config.tfvars" -auto-approve
fi

if [ -d "/var/lib/jenkins/workspace/practice2-1/iac/cicd-edu-cluster" ];then
        cd /var/lib/jenkins/workspace/practice2-1/iac/cicd-edu-cluster
        terraform destroy -var-file="configurations/prod-config.tfvars" -auto-approve
fi
if [ -d "/var/lib/jenkins/workspace/practice2-2/iac/cicd-edu-cluster" ];then
        cd /var/lib/jenkins/workspace/practice2-2/iac/cicd-edu-cluster
        terraform destroy -var-file="configurations/dev-config.tfvars" -auto-approve
fi
DIR=$(echo /var/lib/jenkins/workspace/practice2-3_feature*/iac/cicd-edu-cluster)
if [ -d "$DIR" ];then
        cd /var/lib/jenkins/workspace/practice2-3_feature*/iac/cicd-edu-cluster
        terraform destroy -var-file="configurations/feature-config.tfvars" -auto-approve
fi

cd /practice/cicd-edu*/iac/cicd-edu-cluster/
terraform destroy -var-file="configurations/prod-config.tfvars" -auto-approve
terraform destroy -var-file="configurations/dev-config.tfvars" -auto-approve
terraform destroy -var-file="configurations/feature-config.tfvars" -auto-approve


cd /practice-passive/cicd-edu*/iac/cicd-edu-cluster/
terraform destroy -var-file="configurations/prod-config.tfvars" -auto-approve
terraform destroy -var-file="configurations/dev-config.tfvars" -auto-approve
terraform destroy -var-file="configurations/feature-config.tfvars" -auto-approve
