#!/usr/bin/env bash

#stack_name="sample-aws-xray-rds"
#stack_name="sample-aws-xray-frontend-ecs-cluster"
#stack_name="sample-aws-xray-frontend-alb"
stack_name="sample-aws-xray-dynamodb"
#stack_name="sample-aws-xray-sg"
#stack_name="sample-aws-xray-vpc"

aws cloudformation delete-stack --stack-name ${stack_name}