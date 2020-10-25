# RESTful风格

## 一、概述

> REST全称是Representational State Transfer，中文意思是表述（编者注：通常译为表征）性状态转移。 它首次出现在2000年**Roy Fielding**的博士论文中，Roy Fielding是HTTP规范的主要编写者之一。 他在论文中提到："我这篇文章的写作目的，就是想在符合架构原理的前提下，理解和评估以网络为基础的应用软件的架构设计，得到一个功能强、性能好、适宜通信的架构。REST指的是一组架构约束条件和原则。" 如果一个架构符合REST的约束条件和原则，我们就称它为RESTful架构。
>
> REST本身并没有创造新的技术、组件或服务，而隐藏在RESTful背后的理念就是使用Web的现有特征和能力， 更好地使用现有Web标准中的一些准则和约束。虽然REST本身受Web技术的影响很深， 但是理论上REST架构风格并不是绑定在HTTP上，只不过目前HTTP是唯一与REST相关的实例。 所以我们这里描述的REST也是通过HTTP实现的REST。
>
> ——摘自[菜鸟教程](https://www.runoob.com/w3cnote/restful-architecture.html)

* 菜鸟教程里面有很详尽的解释，本文只介绍基础语义及一些问题

## 二、请求的*语义* 

1. Get：获取资源
   * http://ip:port/rest/persons
   * http://ip:port/rest/person/{id}
   * http://ip:port/rest/person
     * 按条件查询：使用body传输条件
2. post：创建资源
   * http://ip:port/rest/person
3. put：修改资源，修改全部的资源
   * http://ip:port/rest/person
4. patch：修改资源，资源的部分属性
   * http://ip:port/rest/person
5. delete：删除资源
   * http://ip:port/rest/person/{id}
   * http://ip:port/rest/person
     * 按条件删除：使用body传递条件

## 三、一些问题

### 1. 请说一下GET和POST的区别

> GET在浏览器回退时是无害的，而POST会再次提交请求。
> GET产生的URL地址可以被Bookmark，而POST不可以。
> GET请求会被浏览器主动cache，而POST不会，除非手动设置。
> GET请求只能进行url编码，而POST支持多种编码方式。
> GET请求参数会被完整保留在浏览器历史记录里，而POST中的参数不会被保留。
> GET请求在URL中传送的参数是有长度限制的，而POST么有。
> 对参数的数据类型，GET只接受ASCII字符，而POST没有限制。
> GET比POST更不安全，因为参数直接暴露在URL上，所以不能用来传递敏感信息。
> GET参数通过URL传递，POST放在Request body中。
>
> ————————————————
>
> 原文地址：https://mp.weixin.qq.com/s?__biz=MzI3NzIzMzg3Mw==&mid=100000054&idx=1&sn=71f6c214f3833d9ca20b9f7dcd9d33e4#rd

但是就像文章里说的一样，这可能并不是面试官想要的答案

> GET和POST是什么？HTTP协议中的两种发送请求的方法。
>
> HTTP是什么？HTTP是基于TCP/IP的关于数据如何在万维网中如何通信的协议。
>
> HTTP的底层是TCP/IP。所以GET和POST的底层也是TCP/IP，也就是说，GET/POST都是TCP链接。GET和POST能做的事情是一样一样的。你要给GET加上request body，给POST带上url参数，技术上是完全行的通的。 

几句话清晰地解答了GET和POST的区别。

**我认为：**

* GET与POST底层并无区别，POST能传输的数据GET同样能传输
* GET请求关于缓存的特性，我认为这是浏览器厂商根据GET的语义实现的
* POST会发送两次请求这件事也是根据实现厂商的的不同实现而不同
* 它们的区别在于**语义**

### 2. GET请求要不要body？

在微服务流行的今天，如果想使用RESTful风格的api，你会发现，发送GET请求时是有必要包含body的，因为总会有根据条件查询的地方。

在RFC中，官方是这么说的：

> [RFC2616] A server SHOULD read and forward a message-body on any request; if the request method does not include defined semantics for an entity-body, then the message-body SHOULD be ignored when handling the request.

解释出来就是：服务器应该为任何请求读取并转发消息正文；如果请求方法不包含为实体主体定义的语义，则在处理请求时应忽略消息主体。

但是这是2616的标准，而且里面说的也只是**SHOULD**

> [RFC7231] A payload within a GET request message has no defined semantics; sending a payload body on a GET request might cause some existing implementations to reject the request.

这个的意思是说你往GET请求里加body是不符合规范的，不保证所有的实现都支持。意思就是以前的实现可能不支持。

而在7230中：

> [RFC7230] Request message framing is independent of method semantics, even if the method does not define any use for a message body.
>
> 请求消息框架独立于方法语义，即使该方法未定义消息主体的任何用法。

所以，现在的规范并没有说GET不能加请求体(body)，只是因为可能你需要兼容历史版本，所以发送携带body的GET请求时要慎重。ElasticSearch就在使用携带请求体的GET请求作为API，因为工程师认为 `GET` 比 `POST` 能更好的描述信息检索（retrieving information）的行为：https://www.elastic.co/guide/cn/elasticsearch/guide/current/_empty_search.html

**那么，对于我们这些开发者来说呢？**

* 我认为如果我们使用的是微服务架构，正好想要遵从RESTful风格，我们可以允许发送GET请求时携带请求体(body)。但是，**前提是调用方可以发送带请求体的GET请求**
* 所以，对于前端来说，如果我们不想在url路径后面加上很多的参数的话，现在只能使用post代替很多个参数的查询

---

推荐阅读：

1. 菜鸟教程：https://www.runoob.com/w3cnote/restful-architecture.html

2. CSDN-元无心：

   * https://blog.csdn.net/HermitSun/article/details/89889743
   * https://blog.csdn.net/HermitSun/article/details/89880161