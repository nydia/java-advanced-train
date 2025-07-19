# rag
- 向量数据库：通过Milvus的强大向量存储和相似度搜索功能，我们能够高效地管理和检索大规模的Embedding数据。
- Embedding生成：利用预训练的语言模型生成高质量的文本嵌入，为后续的相似度搜索和重排序提供了坚实的基础。
- 大语言模型选型：根据具体需求选择合适的大语言模型，并评估其在不同场景下的表现，确保系统的高效性和准确性。
- 文档切片：通过对长文档进行合理切片，保证每个片段都能被有效处理，提升了整体系统的性能和灵活性。
- 重排序算法：设计并实现高效的重排序算法，进一步优化检索结果的相关性，提升用户体验。
- SpringAI框架使用：借助Spring AI提供的丰富工具和便捷接口，简化了整个开发流程，提高了代码的可维护性和扩展性。

# 问题总结
1. 为什么阿里的百炼需要科学上网才能访问
2. 

## 组件	可选方案
向量存储	            Pinecone、Milvus、Qdrant
Embedding模型	    Hugging Face Sentence-BERT、Cohere
LLM              	ChatGLM-6B、LLaMA-2、Claude
文档解析	            Apache Tika、Docx4j
## Reference
https://www.cnblogs.com/hibpm/p/18900476

## api
http://aith.top:9091/webui/    （这个接口需要科学上网）
http://localhost:8080/milvus/insertDocuments
http://localhost:8080/milvus/ragJsonText

# llm
