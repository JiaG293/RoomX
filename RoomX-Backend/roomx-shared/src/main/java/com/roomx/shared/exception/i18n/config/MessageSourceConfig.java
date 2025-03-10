package com.roomx.shared.exception.i18n.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageSourceConfig {

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("exception/messages"); // Tên base của file properties
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true); // Rất quan trọng!
        return messageSource;
    }
  /* @Autowired
   private ResourceLoader resourceLoader;

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        String basename = "exception/messages";
        messageSource.setBasenames(basename);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);

        // Kiểm tra sự tồn tại của file properties:
        String baseLocation = "classpath:" + basename; // Đường dẫn base của file properties
        String[] locales = {"en", "vi"}; // Liệt kê các locale bạn hỗ trợ

        for (String locale : locales) {
            String resourcePath = baseLocation + "_" + locale + ".properties";
            Resource resource = resourceLoader.getResource(resourcePath);
            if (!resource.exists()) {
                System.err.println("WARNING: Message file not found: " + resourcePath);
                //Nếu file mặc định không tồn tại thì ném exception
                if (locale.equals("en")) {
                    throw new RuntimeException("Missing default message file: " + resourcePath);
                }
            } else {
                System.out.println("Message file found: " + resourcePath);
            }
        }

        return messageSource;
    }*/
}
