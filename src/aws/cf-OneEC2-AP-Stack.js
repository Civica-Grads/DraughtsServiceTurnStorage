{
  "AWSTemplateFormatVersion": "2010-09-09",
  "Description": "AWS CloudFormation HttpD/PHP Stack.",
  "Parameters": {
    "KeyName": {
      "Description": "Name of an existing EC2 KeyPair to enable SSH access to the instance",
      "Type": "AWS::EC2::KeyPair::KeyName",
      "ConstraintDescription": "must be the name of an existing EC2 KeyPair."
    },
    "InstanceType": {
      "Description": "WebServer EC2 instance type",
      "Type": "String",
      "Default": "t1.micro",
      "AllowedValues": [
        "t1.micro",
        "t2.micro",
        "t2.small",
        "t2.medium"
      ],
      "ConstraintDescription": "must be a valid EC2 instance type."
    },
    "SSHLocation": {
      "Description": " The IP address range that can be used to SSH to the EC2 instances",
      "Type": "String",
      "MinLength": "9",
      "MaxLength": "18",
      "Default": "0.0.0.0/0",
      "AllowedPattern": "(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})/(\\d{1,2})",
      "ConstraintDescription": "must be a valid IP CIDR range of the form x.x.x.x/x."
    }
  },
  "Mappings": {
    "AWSInstanceType2Arch": {
      "t1.micro": {
        "Arch": "PV64"
      },
      "t2.micro": {
        "Arch": "HVM64"
      },
      "t2.small": {
        "Arch": "HVM64"
      },
      "t2.medium": {
        "Arch": "HVM64"
      }
    },
    "AWSRegionArch2AMI": {
      "eu-west-1": {
        "PV64": "ami-d6d18ea1",
        "HVM64": "ami-e4d18e93",
        "HVMG2": "ami-72a9f105"
      },
      "eu-central-1": {
        "PV64": "ami-a4b0b7b9",
        "HVM64": "ami-a6b0b7bb",
        "HVMG2": "ami-a6c9cfbb"
      }
    }
  },
  "Resources": {
    "WebServerInstance": {
      "Type": "AWS::EC2::Instance",
      "Metadata": {
        "AWS::CloudFormation::Init": {
          "configSets": {
            "InstallAndRun": [
              "Install",
              "Configure"
            ]
          },
          "Install": {
            "packages": {
              "yum": {
                "httpd": [],
                "php": [],
                "php-mysql": []
              }
            },
            "files": {
              "/var/www/html/index.php": {
                "content": {
                  "Fn::Join": [
                    "",
                    [
                      "<html>\n",
                      "  <head>\n",
                      "    <title>AWS CloudFormation PHP Sample</title>\n",
                      "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\n",
                      "  </head>\n",
                      "  <body>\n",
                      "    <h1>Welcome to the AWS CloudFormation Sample</h1>\n",
                      "    <p/>\n",
                      "    <?php\n",
                      "      // Print out the current data and time\n",
                      "      print \"The Current Date and Time is: <br/>\";\n",
                      "      print date(\"g:i A l, F j Y.\");\n",
                      "    ?>\n",
                      "    <p/>\n",
                      "    <h2>PHP Information</h2>\n",
                      "    <p/>\n",
                      "    <?php\n",
                      "      phpinfo();\n",
                      "    ?>\n",
                      "  </body>\n",
                      "</html>\n"
                    ]
                  ]
                },
                "mode": "000600",
                "owner": "apache",
                "group": "apache"
              },
              "/etc/cfn/cfn-hup.conf": {
                "content": {
                  "Fn::Join": [
                    "",
                    [
                      "[main]\n",
                      "stack=",
                      {
                        "Ref": "AWS::StackId"
                      },
                      "\n",
                      "region=",
                      {
                        "Ref": "AWS::Region"
                      },
                      "\n"
                    ]
                  ]
                },
                "mode": "000400",
                "owner": "root",
                "group": "root"
              },
              "/etc/cfn/hooks.d/cfn-auto-reloader.conf": {
                "content": {
                  "Fn::Join": [
                    "",
                    [
                      "[cfn-auto-reloader-hook]\n",
                      "triggers=post.update\n",
                      "path=Resources.WebServerInstance.Metadata.AWS::CloudFormation::Init\n",
                      "action=/opt/aws/bin/cfn-init -v ",
                      "         --stack ",
                      {
                        "Ref": "AWS::StackName"
                      },
                      "         --resource WebServerInstance ",
                      "         --configsets InstallAndRun ",
                      "         --region ",
                      {
                        "Ref": "AWS::Region"
                      },
                      "\n",
                      "runas=root\n"
                    ]
                  ]
                }
              }
            },
            "services": {
              "sysvinit": {
                "httpd": {
                  "enabled": "true",
                  "ensureRunning": "true"
                },
                "cfn-hup": {
                  "enabled": "true",
                  "ensureRunning": "true",
                  "files": [
                    "/etc/cfn/cfn-hup.conf",
                    "/etc/cfn/hooks.d/cfn-auto-reloader.conf"
                  ]
                }
              }
            }
          },
          "Configure": {
            "commands": {
             }
          }
        },
        "AWS::CloudFormation::Designer": {
          "id": "f580e781-beac-4998-ac84-6060f18fae28"
        }
      },
      "Properties": {
        "ImageId": {
          "Fn::FindInMap": [
            "AWSRegionArch2AMI",
            {
              "Ref": "AWS::Region"
            },
            {
              "Fn::FindInMap": [
                "AWSInstanceType2Arch",
                {
                  "Ref": "InstanceType"
                },
                "Arch"
              ]
            }
          ]
        },
        "InstanceType": {
          "Ref": "InstanceType"
        },
        "SecurityGroups": [
          {
            "Ref": "WebServerSecurityGroup"
          }
        ],
        "KeyName": {
          "Ref": "KeyName"
        },
        "UserData": {
          "Fn::Base64": {
            "Fn::Join": [
              "",
              [
                "#!/bin/bash -xe\n",
                "yum update -y aws-cfn-bootstrap\n",
                "# Install the files and packages from the metadata\n",
                "/opt/aws/bin/cfn-init -v ",
                "         --stack ",
                {
                  "Ref": "AWS::StackName"
                },
                "         --resource WebServerInstance ",
                "         --configsets InstallAndRun ",
                "         --region ",
                {
                  "Ref": "AWS::Region"
                },
                "\n",
                "# Signal the status from cfn-init\n",
                "/opt/aws/bin/cfn-signal -e $? ",
                "         --stack ",
                {
                  "Ref": "AWS::StackName"
                },
                "         --resource WebServerInstance ",
                "         --region ",
                {
                  "Ref": "AWS::Region"
                },
                "\n"
              ]
            ]
          }
        }
      },
      "CreationPolicy": {
        "ResourceSignal": {
          "Timeout": "PT5M"
        }
      }
    },
    "WebServerSecurityGroup": {
      "Type": "AWS::EC2::SecurityGroup",
      "Properties": {
        "GroupDescription": "Enable HTTP access via port 80",
        "SecurityGroupIngress": [
          {
            "IpProtocol": "tcp",
            "FromPort": "80",
            "ToPort": "80",
            "CidrIp": "0.0.0.0/0"
          },
          {
            "IpProtocol": "tcp",
            "FromPort": "22",
            "ToPort": "22",
            "CidrIp": {
              "Ref": "SSHLocation"
            }
          }
        ]
      },
      "Metadata": {
        "AWS::CloudFormation::Designer": {
          "id": "1b1cd479-3065-419a-869d-d4fdc3a12bd1"
        }
      }
    }
  },
  "Outputs": {
    "WebsiteURL": {
      "Description": "URL for newly created LAMP stack",
      "Value": {
        "Fn::Join": [
          "",
          [
            "http://",
            {
              "Fn::GetAtt": [
                "WebServerInstance",
                "PublicDnsName"
              ]
            }
          ]
        ]
      }
    }
  },
  "Metadata": {}
}