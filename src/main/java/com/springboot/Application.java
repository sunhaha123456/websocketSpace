package com.springboot;

import com.springboot.listener.CatListener;
import com.springboot.listener.FishListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// websocket 配置
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}

	@Bean
	StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
		return new StringRedisTemplate(connectionFactory);
	}

	/**
	 * redis消息监听器容器
	 * 可以添加多个监听不同话题的redis监听器，只需要把消息监听器和相应的消息订阅处理器绑定，该消息监听器
	 * 通过反射技术调用消息订阅处理器的相关方法进行一些业务处理
	 * @param connectionFactory
	 * @param connectionFactory
	 * @param catAdapter
	 * @param fishAdapter
	 * @return
	 */
	@Bean
	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
											MessageListenerAdapter catAdapter, MessageListenerAdapter fishAdapter) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		//订阅了一个叫chat 的通道
		container.addMessageListener(catAdapter, new PatternTopic("cat"));
		container.addMessageListener(fishAdapter, new PatternTopic("fish"));
		//这个container 可以添加多个 messageListener
		return container;
	}

	/**
	 * 消息监听器适配器，绑定消息处理器
	 * @return
	 */
	@Bean
	MessageListenerAdapter CatAdapter() {
		return new MessageListenerAdapter(new CatListener());
	}

	/**
	 * 消息监听器适配器，绑定消息处理器
	 * @return
	 */
	@Bean
	MessageListenerAdapter FishAdapter() {
		return new MessageListenerAdapter(new FishListener());
	}
}