package io.kimmking.cache.redistemplate;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @Auther: hqlv
 * @Date: 2021/7/18 23:59
 * @Description:
 */
public class ProtobufSerializer<T> implements RedisSerializer<T> {

    // RuntimeSchema是一个包含业务对象所有信息的类，包括类信息、字段信息
    Schema<ProtoStuffWrapper> schema = RuntimeSchema.getSchema(ProtoStuffWrapper.class);

    @Override
    public byte[] serialize(T t) {
        if(t == null){
            return null;
        }
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        byte[] bs = ProtostuffIOUtil.toByteArray(new ProtoStuffWrapper<>(t), schema, buffer);
        return bs;
    }

    @Override
    public T deserialize(byte[] bytes) {
        ProtoStuffWrapper<T> protoStuffWrapper = new ProtoStuffWrapper();
        ProtostuffIOUtil.mergeFrom(bytes, protoStuffWrapper, schema);
        return protoStuffWrapper.getT();
    }

    /**
     * 序列化包装类，深度克隆，避免无法获取schema
     *
     * @param <T> 业务对象
     */
    public static class ProtoStuffWrapper<T> implements Cloneable {
        private T t;

        ProtoStuffWrapper() {
        }

        ProtoStuffWrapper(T t) {
            this.t = t;
        }

        public T getT() {
            return t;
        }

        public void setT(T t) {
            this.t = t;
        }

        @Override
        public ProtoStuffWrapper<T> clone() {
            try {
                return (ProtoStuffWrapper<T>) super.clone();
            } catch (CloneNotSupportedException e) {
                return new ProtoStuffWrapper<T>();
            }
        }
    }

}
