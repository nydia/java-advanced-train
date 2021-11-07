## open-platform 开放平台


## 代码工程说明


## 配置管理员要求的 代码目录约定 ##

.gitignore 忽略的文件，一些跟编译器、临时文件相关，根据不同的开发小组设置不同，见：　https://www.gitignore.io/

build.sh 编译脚本，见 http://wiki.iboxpay.com/pages/viewpage.action?pageId=8487411

README.md 简单描述系统功能以及代码编译说明

CHANGELOG.md 代码修改记录，参看 https://gitlab.com/gitlab-org/omnibus-gitlab/blob/master/CHANGELOG.md

sonar-project.properties java项目里需要添加，代码规范检查工具sonar的配置文件，见 https://git.iboxpay.com/docs/iboxpay/wikis/sonar


## 项目准备

1. 启动本地两台mysql，端口分别为3316、3326，启动命令 mysqld --console