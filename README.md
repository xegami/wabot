# wabot
Standalone WhatsApp chatbot. Add your own bot to your WhatsApp chat groups or for whatever business you need. Right now, the purpose of the bot is to obtain League of Legends players statistics and provide them to the desired chat.

## How to install
Check out the *Constants* class in the *core* folder. There, add the last Selenium driver to the path it says (or change it if you don't like),
a .json file where you put your chat friends contact names and LoL names (or whatever game you adapt the bot at) aswell as the API keys you need.

So now, just "java -cp wabot.jar com.xegami.wabot.Main" the compiled .jar you can find in the *releases* tab an it will start running. 
Take in consideration that you will need an alternative WhatsApp account/number to have access to the WhatsApp web page.

## How to use
All the commands avaliable that I made are in the *TftService* class. Modify them at your wish.

## Contact
For any questions: devetoper@gmail.com
