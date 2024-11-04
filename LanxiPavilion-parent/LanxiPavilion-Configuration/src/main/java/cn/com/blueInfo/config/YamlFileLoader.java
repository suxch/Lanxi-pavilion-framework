package cn.com.blueInfo.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class YamlFileLoader implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        Properties yaml = loadYamlIntoProperties(resource);
        if (Objects.isNull(yaml)) {
            return (!StringUtils.isEmpty(name) ? new ResourcePropertySource(name, resource) : new ResourcePropertySource(resource));
        }
        String fileName = !StringUtils.isEmpty(name) ? name : resource.getResource().getFilename();
        if (StringUtils.isEmpty(fileName)) {
            throw new RuntimeException("加载YAML资源文件失败" + resource);
        }
        return new PropertiesPropertySource(fileName, yaml);
    }

    private Properties loadYamlIntoProperties(EncodedResource resource) {
        YamlPropertiesFactoryBean factoryBean = new YamlPropertiesFactoryBean();
        factoryBean.setResources(resource.getResource());
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

}
