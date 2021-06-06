package com.practica.proyectoFinal.util;

 

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

 

/**
 * The Class MessageMultiLanguageComponent.
 */
@Component
@Configuration
public class MessageMultiLanguageComponent {

 

    /** The basename. */
    @Value("${spring.messages.basename}")
    private String basename;

 

    /** The encoding. */
    @Value("${spring.messages.encoding}")
    private String encoding;

 

    /**
     * Gets the text.
     *
     * @param key the key
     * @return the text
     */
    public String getText(String key) {
        return messageSource().getMessage(key, null, LocaleContextHolder.getLocale());
    }

 

    /**
     * Gets the text.
     *
     * @param key the key
     * @param args the args
     * @return the text
     */
    public String getText(String key, Object[] args) {
        return messageSource().getMessage(key, args, LocaleContextHolder.getLocale());
    }

 

    /**
     * Gets the validator.
     *
     * @return the validator
     */
    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

 

    /**
     * Message source.
     *
     * @return the message source
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource message = new ReloadableResourceBundleMessageSource();
        message.setBasename("classpath:" + basename);
        message.setDefaultEncoding(encoding);
        return message;
    }
}