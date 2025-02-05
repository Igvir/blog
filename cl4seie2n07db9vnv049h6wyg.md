---
title: "Invoke Lambda Functions Across AWS Accounts"
seoTitle: "Invoke Lambda Functions Across AWS Accounts"
seoDescription: "A Lambda networking POC using multiple AWS accounts"
datePublished: Fri Jun 24 2022 12:00:00 GMT+0000 (Coordinated Universal Time)
cuid: cl4seie2n07db9vnv049h6wyg
slug: invoke-lambda-functions-across-aws-accounts
cover: https://cdn.hashnode.com/res/hashnode/image/upload/v1655938053944/2VvyxPapR.jpg
tags: aws, networking, serverless, aws-lambda

---

Recently, while working on a project assignment, I had to check the options for invoking a Lambda function across AWS accounts. The question was simple: Is it possible to call a Lambda function from a source account, which I will call account 1, to a target account (account 2) in AWS? Checking the documentation we knew it was possible, but there were additional factors to consider: The Lambda function in account 2 would be defined in a VPC and assigned to a private subnet with no internet access. Which raised another question: What additional configuration was required for this scenario? The attached diagram below represents the case we review in this article.

![service_diagram](https://cdn.hashnode.com/res/hashnode/image/upload/v1655938255995/eVL2DdXQz.png align="center")

The [use of multiple accounts within the same organization](https://docs.aws.amazon.com/whitepapers/latest/organizing-your-aws-environment/benefits-of-using-multiple-aws-accounts.html) is a best practice for managing cloud solutions. It allows you to group workloads, manage costs, apply security controls, and respond dynamically to business needs. But, when you are working with AWS, crossing the account border could be a security configuration nightmare.

## IAM Roles and Policies

Lambda functions need an execution Role. A Lambda function's execution role is an AWS Identity and Access Management (IAM) role that grants the function permission to access AWS services and resources. You provide this role when you create a function, and Lambda assumes the role when your function is invoked. In our case,  the source Lambda, located in account 1, must have the permissions to invoke a Lambda function and be able to access the target Lambda function, located in account 2. An example of the policy would be:

![policy.png](https://cdn.hashnode.com/res/hashnode/image/upload/v1655938513349/qppw_2f7V.png align="left")

A Lambda function can be called synchronously or asynchronously. The above policy grants both ``Lambda:InvokeFunction`` and ``Lambda:InvokeAsync`` permissions. Our goal is to grant the least privilege, so we should only give permission for the action that is required.

In the case of the target function, it is necessary to grant invocation permissions to the role assumed by the source function. It creates a trust relationship that allows executing the task of crossing the border between our two accounts. It is necessary for services or for any other AWS account that requires permissions to call a function from their resource-based policy.  Refer to the documentation for more details about [resource-based policies for AWS Lambda](https://docs.aws.amazon.com/lambda/latest/dg/access-control-resource-based.html?icmpid=docs_lambda_help). You can add permissions to the resource-based policy using the AWS CLI. Permissions can be granted to a role, a service, or the entire organization. Again the idea is to be granular in assigning permissions.

Based on the example shown in the documentation we created the command line below to grant permissions. The command specifies the account ID as principal ``111122223333`` to invoke ``my-function`` with the alias ``prod``.

![command-line](https://cdn.hashnode.com/res/hashnode/image/upload/v1655940385024/1XU_mAMW7.png align="left")

## Lambda Function Restrictions in a VPC

By default, the Lambda function runs its functions in a secure VPC. But this is a VPC owned by the AWS Lambda service and is not connected to the default VPC of the account that we manage in AWS. When a function is located within an account's VPC, the Lambda cannot access the Internet unless its VPC provides access.

Lambda accesses resources in your VPC using an ENI Hyperplane. Hyperplane uses a network interface for access between AWS accounts.

When placing a Lambda function in a VPC we encounter three limitations that can affect execution:


1. *EC2ThrottledException*: It will occur if the VPC does not have enough ENIs or IPs in the subnet. You will need to configure the VPC subnets (and therefore the VPC) to have enough IP addresses.
1. *Client.NetworkInterfaceLimitExceeded*: Thanks to a recent enhancement, Lambda function scaling is no longer directly related to the number of network interfaces. Hyperplane ENIs can now scale to support a large number of concurrent function executions. However, there is a limit to the number of ENIs per region, and this needs to be considered. See [improved VPC networking for AWS Lambda functions](https://aws.amazon.com/blogs/compute/announcing-improved-vpc-networking-for-aws-lambda-functions/) for more details.
1. Client.RequestLimitExceeded: The lambda functions reach the create network interface (ENI) request rate limit. If you have concurrency spikes in your lambda functions, you might hit this limit.

## Conclusions

The POC verified that calling Lambda functions across AWS accounts is possible and even relatively easy to do with the right permissions and configurations. However, when it comes to functions running within a VPC, you have to consider the restrictions associated with networking Lambda functions in AWS and their potential impacts on the design of a full load testing scheme. Regarding the configurations required for the analyzed case, it was determined that permissions must be granted both at the origin and at the destination side. For the origin function, permissions to use the API of Lambda functions must be granted, while in the target, a resource policy must be established that grants a trust relationship to the source account.


