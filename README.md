#Commands4J
Commands4J is an easy-to-use command framework for [Discord4J](http://github.com/austinv11/Discord4J).

I could sit here and talk about how great Commands4J is, but that'd be pointless. Let me show you.

Add a command:

```
Command ping = new Command("ping", new Command.Options().withAliases("testAlias", "testAlias1"))
					.onExecuted(context -> {
						//Put what you want the command to do here.
						//You can even easily get the arguments of the command easily!
						String args[] = context.getArgs();
					})
					.onFailure((context, reason) -> {
						//If the command fails, put what should happen here.
					});
```

Then all you have to do is register it! 

`CommandRegistry.getRegistryForClient(the client object).register(ping);`

Isn't that easy? Using Commands.Options, you can even easily add aliases, require specific permissions, and more!

The default prefix is always `!`, but you can easily set it using `CommandRegistry.setPrefix("prefix")`.

#Errors and Contributors
##Errors
If there is a problem with Commands4J please feel free to either open an issue or give us feedback on the [Discord4J official server](https://discord.gg/NTC87qe).
##Contributors
* Austinv11 - Making the orginal Discord4J Library!
* GrandPanda - Most of the base code and first commit!
* poncethecat - Various small things (including this readme!)


