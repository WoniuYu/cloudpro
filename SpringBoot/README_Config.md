获取配置文件配置

~~~java
@ConfigurationProperties(prefix = "my.cloud")
@Component
@Data
public class MyCode {
    private String code;
	private String name;
	public void test() {
		System.out.println(code); //  1
		System.out.println(name); //  woniu
	}
}
~~~

application.properties

~~~properties
my.cloud.code=1
my.cloud.name=woniu
~~~





