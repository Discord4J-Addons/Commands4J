#Commands4J [![](https://jitpack.io/v/Discord4J-Addons/Commands4J.svg)](https://jitpack.io/#Discord4J-Addons/Commands4J)
Commands4J is an easy-to-use functional command framework for [Discord4J](http://github.com/austinv11/Discord4J).

##Adding Commands4J as a dependency
###With Maven:
```xml
<repository>
	<id>jitpack.io</id>
	<url>https://jitpack.io</url>
</repository>

<dependency>
	<groupId>com.github.Discord4J-Addons</groupId>
	<artifactId>Commands4J</artifactId>
	<version>1.0.2</version>
</dependency>
```

###With Gradle:
```groovy
maven { url 'https://jitpack.io' }

compile 'com.github.Discord4J-Addons:Commands4J:1.0.2'
```

##Using Commands4J
Creating commands with C4J is extremely simple. At the very least, a command needs a name and a function to execute when
the command is called. For example, this is a simple "ping" command:
```java
Command ping = new Command("ping")
		.withDescription("Ping -> Pong!")
		.onExecuted(context ->
			context.getMessage().getChannel().sendMessage("Pong!");
		);

IDiscordClient client = ...; // Client gotten from Discord4J
CommandRegistry.getForClient(client).register(ping);
```
The command class also has other chaining methods to add aliases, set auto-delete, require permissions, and more!

##Additional Info
You can contact me on the [Official Discord4J Server](https://discord.gg/NxGAeCY) for help with this API.

