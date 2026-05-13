package model.user;

public sealed class User permits Guest, Member, TicketsClerk, Projectionist {
        
}