package application.config.refresh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.stereotype.Component;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;

@Component
public class ApolloConfigChanged {

	@Autowired
	private RefreshScope refreshScope;

	@ApolloConfigChangeListener
	private void configChangeHandler(ConfigChangeEvent changeEvent) {

		refreshScope.refreshAll();
	}
}