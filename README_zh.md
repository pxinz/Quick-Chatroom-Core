# Quick-Chatroom-Client

> 📌 Tips:  
> 作者的英文比较差，所以大部分注释和文档都是用中文写的。  
> 请尽量不要看那些充斥着语法错误的英文文档！  
> [English version(英文版)](https://github.com/pxinz/Quick-Chatroom-Core/blob/master/README.md)

## 简介

`目标:` 制作一个尊重用户隐私的noIM(not only IM)软件  
`语言:` Java 11 & Kotlin  
`进度:` 正在重构制作软件框架

## 计划

### 平台支持

| 项目/平台    | Windows 10/11 | MacOS | Android | Web |
|----------|:-------------:|:-----:|:-------:|:---:|
| 文字(包括帖子) |      📌       |  📌   |   📌    | 📌  |
| 语音       |      📌       |  📌   |   📌    |  ❌  |
| 可拓展性     |      📌       |  📌   |    ❌    |  ❌  |

> ✅ - 支持，且经过测试  
> ❓ - 支持，但未经过测试  
> 📌 - 计划支持  
> ❌ - 不计划支持

### To do

| 项目     | 进度                                                                        |
|--------|---------------------------------------------------------------------------|
| 重构软件框架 | 60%                                                                       |
| 实现文字聊天 | 50%                                                                       |
| 实现多频道  | 0%                                                                        |
| 开发客户端  | 见 [Quick-Chatroom-Client](https://github.com/pxinz/Quick-Chatroom-Client) |
| 开发服务端  | 见 [Quick-Chatroom-Server](https://github.com/pxinz/Quick-Chatroom-Server) |
| 完成文档   | 长期                                                                        |

## 依赖项

| 依赖项                                               | 用途     | 计划                                                      |
|---------------------------------------------------|--------|---------------------------------------------------------|
| [TinyLog](https://github.com/tinylog-org/tinylog) | 打印日志   |                                                         |
| [fastjson](https://github.com/alibaba/fastjson)   | JSON读写 | 使用 [fastjson2](https://github.com/alibaba/fastjson2) 替换 |

