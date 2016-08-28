package com.darichey.discord.api;

import sx.blah.discord.handle.obj.IMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandContext {

	private final IMessage message;
	private final String name;
	private final String[] args;

	public CommandContext(IMessage message) {
		this.message = message;
		if(!message.getContent().contains(" ")) {
		    if(CommandRegistry.getRegistryForClient(message.getClient()).getPrefix().length() > 1) {
                String noArgs[] = {};
                this.args = noArgs;
                this.name = message.getContent().substring(CommandRegistry.getRegistryForClient(message.getClient()).getPrefix().length());
                return;
            }
		    String noArgs[] = {};
		    this.args = noArgs;
            this.name = message.getContent().substring(CommandRegistry.getRegistryForClient(message.getClient()).getPrefix().length()).substring(0, message.getContent().contains(" ") ? message.getContent().indexOf(" ") + 1 : message.getContent().length() - 1);
            return;

        } else {
            
            String temp = message.getContent().substring(CommandRegistry.getRegistryForClient(message.getClient()).getPrefix().length()).substring(0, message.getContent().contains(" ") ? message.getContent().indexOf(" ") + 1 : message.getContent().length() - 1);
            this.name = temp.substring(0, temp.length() - 2);
            List<String> list = new ArrayList<String>();
            Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(message.getContent().substring(this.name.length() + 2).replace(name + " ", ""));
            while (m.find()) {
                list.add(m.group(1).replace("\"", ""));
            }
            String args[] = new String[list.size()];
            this.args = list.toArray(args);
            return;
        }
	}

	public CommandContext(IMessage message, String name, String[] args) {
		this.message = message;
		this.name = name;
		this.args = args;
	}

	public IMessage getMessage() {
		return this.message;
	}

	public String getName() {
		return this.name;
	}

	public String[] getArgs() {
		return this.args;
	}

}
