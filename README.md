# mcp-jira-issue-search-server

## Quick Start
 - Start jira locally and make sure your server is started on localhost:2900
 - build docker image with command:  
````
   docker build -t jira-mcp .
````
 - start docker service on port 8080 and navigate to web page
````
   docker run -d -p 8080:8080 jira-mcp
````

Hello, there is my quick demo
The idea is to translate russian speach to english with AI and then prepare JQL search using promt rules.
After that searching jira issues via REST api according to the JQL.

So firstly let's check the number of issues: 5
All of them have different summaries
...and Only one with platform network problems

Words I'm saying: "Покажи все баги, у которых есть проблемы с платформой" ("Show all bugs that have problems with the platform")
As results we see only one issue AAA-3 in the response

[Jira-MCP-Server-Part1.mov](src%2Fmain%2Fresources%2Fstatic%2Fdemo%2FJira-MCP-Server-Part1.mov)
[Jira-MCP-Server-Part2.mov](src%2Fmain%2Fresources%2Fstatic%2Fdemo%2FJira-MCP-Server-Part2.mov)