# This library is DEFUNCT
Use [Discord4J v3](https://discord4j.com) instead!

This library was created for Discord4J v2 which is no longer supported or maintained. 

# Commands4J
An API for making chat commands with Discord4J

## Adding Commands4J as a Dependency
```groovy
repositories {
  maven { url 'https://jitpack.io' }
}

dependencies {
  compile 'com.github.Discord4J-Addons:Commands4J:VERSION'
}
```

## Using Commands4J
Commands4J uses the builder pattern to construct Commands. Here is an example of a simple "ping" commnad:
```java
Command ping = Command.builder()
    .onCalled(ctx -> {
      ctx.getChannel().sendMessage("Pong!");
    })
    .build();
```
The `CommandContext` object gives access to information about the context in which the command was executed such as the channel and arguments.

Then, the command must be registered with a `CommandRegistry` which is the main way to configure and interact with Commands4J. After the commands are added, simply register a `CommandListener` configured with the registry with Discord4J.
```java
IDiscordClient client = ... // obtained from Discord4J
CommandRegistry registry = new CommandRegistry("!");
registry.register(ping, "ping");
client.getDispatcher().registerListener(new CommandListener(registry));
```

## Limiters
Commands4J uses the concept of "limiters" to limit the scope in which a command can be executed. A limiter is simply a function which takes a `CommandContext` and returns whether the command can execute. Commands4J provides many default implementations of `Limiter`, but it is easy to define your own.
