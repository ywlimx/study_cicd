PROJECT_NAME=$(gcloud config get-value project)
find ./ -name "*.yml" -exec sed -i "s/_PROJECT_/$PROJECT_NAME/g" {} \;
find ./ -name "*.tfvars" -exec sed -i "s/_PROJECT_/$PROJECT_NAME/g" {} \;
find ./ -name "*.tf" -exec sed -i "s/_PROJECT_/$PROJECT_NAME/g" {} \;
cdate=`date +%s`
sed -i "s/_CDATE_/$cdate/g" practice2.yml
