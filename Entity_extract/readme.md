###########任务介绍
1. Assignment1
    观察实体的abstract属性文本，人工总结模板（正则表达式），使用模板从abstract文本中抽取指定的属性值。
2. Assignment2
    利用人工标注，实体的abstract、aliases等属性值去除非佛教寺庙实体。

###########开发环境
1. JRE : 1.8.0_31 b13 amd64
2. IDE : IntelliJ IDEA 2016.3.4

###########目录结构
├── .idea
├── out
├── resource						// 资源文件夹，包含任务用到的所有数据
│   ├── Assignment1
│   │	└── abstracts.ttl			// 佛教寺庙知识图谱中实体的摘要属性
│   └── Assignment2
│   	├── entities_labeled		// 四个工人分别对entities.txt中实体的标注结果，文件中仅保留标注工人认为是佛教寺庙的实体
│	    │	├── 1.txt				// 1号工人标注结果
│		│	├── 2.txt				// 2号工人标注结果
│		│	├── 3.txt				// 3号工人标注结果
│		│	└── 4.txt				// 4号工人标注结果
│    	├── abstracts.ttl			// entities.txt文件中实体的摘要属性
│    	├── aliases.ttl				// entities.txt文件中实体的别名属性
│    	├── categories.ttl			// entities.txt文件中实体的类别属性
│     	├── entities.txt			// 待过滤的全部佛教寺庙实体文件，包含噪声数据
│    	└── sections.ttl			// entities.txt文件中实体的宗派属性
├── src
│   └── Assignment.java				//源代码文件
├── Assignment.iml
└── readme.md						// help

###########函数功能
1. void extract(String str, String regex)
	功能	给定字符串与正则表达式，打印所有匹配的子串
	参数	String str : 带匹配的字符串
			String regex : 模板(正则表达式)
2. void extractFile(String input, String regex)
	功能	给定知识库的abstract文件与正则表达式，使用正则从abstract中抽取属性值，每抽出一条属性值打印一行abstract一行属性的主语、宾语对
	参数	String input : 实体abstract属性文件的完整路径
			String regex : 模板
3. HashSet<String> clean(String path, int threshold)
	功能	给定人工标注文件路径和阈值，被标注为正确实体的次数不小于阈值则被视为正确实体返回，否则被视为错误实体打印出来
	参数	String path : 人工标注结果文件的路径
			int threshold : 阈值
4. HashSet<String> clean(CleanMethod method, HashSet<String> entities, String attrFile, String[] filters)
	功能	给定实体和知识库的属性文件，属性值中包含过滤词的实体被视为错误实体打印出来，不包含过滤词的实体被视为正确实体返回
	参数	CleanMethod method : 数据清洗使用的属性，可选属性包括：CleanAttr.ABSTRACT, CleanAttr.CATEGORY, CleanAttr.NAME, CleanAttr.SECTION
			HashSet<String> entities : 待过滤的集体集合
			String aliasFile : 实体属性文件完整路径
			String[] filters :  过滤词







