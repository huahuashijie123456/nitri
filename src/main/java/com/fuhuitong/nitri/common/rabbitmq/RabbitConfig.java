package com.fuhuitong.nitri.common.rabbitmq;

/**
 * @Author Wang
 * @Date 2019/5/6 0006 12:19
 **/

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {



    @Bean
    public Queue QueueA() {
        return new Queue("yeepay");
    }



    @Bean
    public Queue QueueB() {
        return new Queue("helloObj");
    }


    @Bean
    public Queue QueueC() {
        return new Queue("udyhy");
    }

    @Bean
    public Queue QueueD() {
        return new Queue("yeepaywith");
    }

    @Bean
    public Queue QueueE() {
        return new Queue("chanjiePay");
    }

    /**
     * Fanout 就是我们熟悉的广播模式或者订阅模式，给Fanout交换机发送消息，绑定了这个交换机的所有队列都收到这个消息。
     * @return
     */
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("ABExchange");
    }



    @Bean
    Binding bindingExchangeA(Queue QueueA, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(QueueA).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeB(Queue QueueB, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(QueueB).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeC(Queue QueueC, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(QueueC).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeD(Queue QueueD, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(QueueD).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeE(Queue QueueE, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(QueueE).to(fanoutExchange);
    }


}