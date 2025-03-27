package threadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;

public class NydiaThreadLocal {

    private static final TransmittableThreadLocal<String> context = new TransmittableThreadLocal<>();


}
