package com.example.demo.service;

import com.example.demo.model.Goods;
import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.model.Warehouse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @Auther: hqlv
 * @Date: 2022/6/30 22:37
 * @Description:
 */
@Service
public class OrderService {

    @Autowired
    private AsyncRestTemplate asyncRestTemplate;

    @Autowired
    private WebClient webClient;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public void getOrderByRest(DeferredResult<Order> rs, long orderId) {
        // [1]
        Order order = mockOrder(orderId);
        // [2]
        ListenableFuture<ResponseEntity<User>> userLister = asyncRestTemplate.getForEntity("http://user-service/user/mock/" + 1, User.class);
        ListenableFuture<ResponseEntity<List<Goods>>> goodsLister =
                asyncRestTemplate.exchange("http://goods-service/goods/mock/list?ids=" + "1,2",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Goods>>() {
                        });
        ListenableFuture<ResponseEntity<List<Warehouse>>> warehouseLister =
                asyncRestTemplate.exchange("http://warehouse-service/warehouse/mock/list?ids=" + "1,2",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Warehouse>>() {
                        });
        // [3]
        CompletableFuture<ResponseEntity<User>> userFuture = userLister.completable().exceptionally(err -> {
            logger.warn("get user err", err);
            return new ResponseEntity(new User(), HttpStatus.OK);
        });
        CompletableFuture<ResponseEntity<List<Goods>>> goodsFuture = goodsLister.completable().exceptionally(err -> {
            logger.warn("get goods err", err);
            return new ResponseEntity(new ArrayList<>(), HttpStatus.OK);
        });
        // [4]
        CompletableFuture<ResponseEntity<List<Warehouse>>> warehouseFuture = warehouseLister.completable().exceptionally(err -> {
            logger.warn("get Warehouse err", err);
            return new ResponseEntity(new ArrayList<>(), HttpStatus.OK);
        });

        /*
        warehouseFuture.thenCombineAsync(goodsFuture, (warehouseRes, goodsRes) -> {
           order.setWarehouse(warehouseRes.getBody());
            List<Goods> goods = goodsRes.getBody().stream()
                    .filter(g -> g.getPrice() > 10).limit(5)
                    .collect(Collectors.toList());
            order.setGoods(goods);
            return order;
        }).whenCompleteAsync((o, err) -> {
            // [5]
            if (err != null) {
                logger.warn("err happen:", err);
            }
            rs.setResult(o);
        });
        */

    }

    public Mono<Order> getOrder(long orderId) {
        // [1]
        Mono<Order> orderMono = mockOrderMono(orderId);
        // [2]
        return orderMono.flatMap(o -> {
            // [3]
            Mono<User> userMono = getMono("http://user-service/user/mock/" + o.getUserId(), User.class).onErrorReturn(new User());
            Flux<Goods> goodsFlux = getFlux("http://goods-service/goods/mock/list?ids=" +
                    StringUtils.join(o.getGoodsIds(), ","), Goods.class)
                    .filter(g -> g.getPrice() > 10)
                    .take(5)
                    .onErrorReturn(new Goods());
            // [4]
            return userMono.zipWith(goodsFlux.collectList(), (u, gs) -> {
                o.setUser(u);
                o.setGoods(gs);
                return o;
            });
        });
    }

    //如果接口参数同时传入了订单id，仓库id，产品id，我们也可以同时获取这三个数据，再组装起来
    public Mono<Order> getOrder(long orderId, long warehouseId, List<Long> goodsIds) {
        Mono<Order> orderMono = mockOrderMono(orderId);

        return orderMono.zipWith(getMono("http://warehouse-service/warehouse/mock/" + warehouseId, Warehouse.class), (o, w) -> {
            o.setWarehouse(w);
            return o;
        }).zipWith(getFlux("http://goods-service/goods/mock/list?ids=" +
                StringUtils.join(goodsIds, ","), Goods.class)
                .filter(g -> g.getPrice() > 10)
                .take(5)
                .collectList(), (o, gs) -> {
            o.setGoods(gs);
            return o;
        });
    }

    //如果我们需要串行获取订单，仓库，商品这三个数据，实现如下
    //zipWith方法可以组合两个Mono，并返回新的Mono类型，这里组合仓库、产品数据，最后返回Mono
    public Mono<Order> getOrderInLabel(long orderId) {
        Mono<Order> orderMono = mockOrderMono(orderId);

        return orderMono.zipWhen(o -> getMono("http://warehouse-service/warehouse/mock/" + o.getWarehouseId(), Warehouse.class), (o, w) -> {
            o.setWarehouse(w);
            return o;
        }).zipWhen(o -> getFlux("http://goods-service/goods/mock/list?ids=" +
                StringUtils.join(o.getGoodsIds(), ",") + "&label=" + o.getWarehouse().getLabel() , Goods.class)
                .filter(g -> g.getPrice() > 10)
                .take(5)
                .collectList(), (o, gs) -> {
            o.setGoods(gs);
            return o;
        });
    }



    public Order mockOrder(long orderId) {
        return null;
    }

    public Mono<Order> mockOrderMono(long orderId) {
        return null;
    }

    private <T> Mono<T> getMono(String url, Class<T> resType) {
        return webClient.get().uri(url).retrieve().bodyToMono(resType);
    }

    private <T> Flux<T> getFlux(String url, Class<T> resType) {
        return webClient.get().uri(url).retrieve().bodyToFlux(resType);
    }


}
