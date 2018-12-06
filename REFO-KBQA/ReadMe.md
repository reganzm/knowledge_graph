
# 基于知识图谱的对话系统

## 1.本体设计

### 采用protege本体编辑工具编辑本体，protege工具下载地址：https://protege.stanford.edu/download/protege/


```python
from IPython.display import Image
Image('本体设计.png')
```




![png](output_3_0.png)



#### 从protege导出保存为ontolegy.owl文件

## 2.抓取数据

#### 数据来源 themoviedb:https://www.themoviedb.org
#### 需要先注册个人账号，申请API Key,使用crawler中的movie_crawler.py抓取电影信息存入mysql数据库

#### API Key (v3 auth)
#### 949919ff571a16cb26fbb04763bd89a4

#### API Read Access Token (v4 auth)
#### eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5NDk5MTlmZjU3MWExNmNiMjZmYmIwNDc2M2JkODlhNCIsInN1YiI6IjViZjAxNjM3YzNhMzY4MThhNTAzM2Y3NCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ims8E41Sf1djeuTe7IUnVtlN6T9rbc2RjlwBWOV9DpU


```python
from IPython.display import Image
Image('项目结构.png')
```




![png](output_7_0.png)



#### 使用上面项目中的crawler文件夹中的movie_crawler.py抓取数据

#### 下面是抓取电影数据形成的表格


```python
from IPython.display import Image
Image('表.png')
```




![png](output_10_0.png)



## 3.安装jena

#### 使用jena来存储RDF图数据信息，且jena带有fuseki sparql endpoint和几种实现的推理机
#### 基于docker 安装，安装命令：docker pull docker.io/stain/jena-fuseki
#### 启动容器：docker run -d -p 3030:3030 -e 'ADMIN_PASSWORD=jena' --name jena docker.io/stain/jena-fuseki
#### 通过-e参数指定容器的环境变量，通过环境变量设置ADMIN_PASSWORD密码

#### docker images
#### REPOSITORY                          TAG                 IMAGE ID            CREATED             SIZE
#### docker.io/neo4j                     latest              ca42dc30bb07        3 weeks ago         189 MB
#### docker.io/stain/jena-fuseki         latest              63bd384372da        7 months ago        144 MB
#### docker.io/mobz/elasticsearch-head   5                   b19a5c98e43b        22 months ago       824 MB

#### 启动容器后，可在浏览器中输入ip地址进行访问了


```python
from IPython.display import Image
Image('fuseki.png')
```




![png](output_14_0.png)



## 4.安装d2rq

#### d2rq是一个将关系数据库映射为RDF的工具，下载地址为：http://d2rq.org/
#### 分别使用generate-mapping 工具生成映射文件和nt文件

#### -u root
#### -p Root@2018
#### -o 输出映射文件
#### jdbc:mysql://kg_demo_movie mysql中的表
#### 生成关系数据库表和定义本体之间的映射文件
#### ./generate-mapping -u root -p Root@2018 -o kg_demo_movie_mapping.ttl jdbc:mysql:///kg_demo_movie
#### 使用生成的映射文件，生成nt文件
#### ./dump-rdf -o kg_demo_movie.nt kg_demo_movie_mapping.ttl

#### 运行上面命令会在当前目录下生成xxx.nt文件，在jena fuseki 网页界面中新建tdb数据库，导入生成的nt文件


```python
from IPython.display import Image
Image('导入数据.png')
```




![png](output_19_0.png)



## 5.REFO正则表达式切分，构建查询SPARQL模板，查询jena中存放的数据，返回结果

#### 代码位于KB_quary/quary_main.py，运行如下


```python
from IPython.display import Image
Image('演示.png')
```




![png](output_22_0.png)


