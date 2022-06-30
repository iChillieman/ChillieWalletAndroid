
# Architecture:
- This project is using an MVVM architecture heavily relying on LiveData, Dagger, and RxJava2. 
- Any Written code from Chillieman is done in Kotlin. There are some files that were automatically generated in java by the Web3j Library, but those will be converted into Kotlin as i want this project to be 100% Kotlin.
- The databse uses Room, and DAOs are used to communicate with the Database.
- Repositories are responsible for managing Cached data, databse communications, and fetching data from the Web.
- Managers use the Repositories and other injected classes to abstract business logic.
- ViewModels fetch results from Managers & Repositories, updating LiveData, which is observed by the UI components, and used to update the screen.

# Recruiters:
Check out the following components that i think are pretty cool.

**Encryption:**
- Master Key Created when [App](https://github.com/iChillieman/ChillieWalletAndroid/blob/master/app/src/main/java/com/chillieman/chilliewallet/ChillieApplication.kt#L48) is created for first time:
- Dagger [Provides](https://github.com/iChillieman/ChillieWalletAndroid/blob/master/app/src/main/java/com/chillieman/chilliewallet/di/ProviderModule.kt#L41) the Master Key and Cipher
- [Encryption Manager](https://github.com/iChillieman/ChillieWalletAndroid/blob/master/app/src/main/java/com/chillieman/chilliewallet/manager/EncryptionManager.kt) is used to easily decrypt and encrypt text. 
 - Manager is used by [AuthRepository](https://github.com/iChillieman/ChillieWalletAndroid/blob/master/app/src/main/java/com/chillieman/chilliewallet/repository/AuthRepository.kt#L30) to secure any sensative data that interacts with the database.

**Auth Lockout:**
- While using this app, you must be authenticated using a PIN code.
- [AuthManager](https://github.com/iChillieman/ChillieWalletAndroid/blob/master/app/src/main/java/com/chillieman/chilliewallet/manager/AuthManager.kt#L25) is responsible for maintaining the Authentication state of a user. 
- [AuthService is always watching the clock](https://github.com/iChillieman/ChillieWalletAndroid/blob/master/app/src/main/java/com/chillieman/chilliewallet/service/AuthService.kt#L40), waiting for time to run out
- [MainActivity will launch the AuthActivity](https://github.com/iChillieman/ChillieWalletAndroid/blob/master/app/src/main/java/com/chillieman/chilliewallet/ui/main/MainActivity.kt#L50), preventing use of the app until the correct pincode is entered.


# ChillieWallet
ChillieWallet is an upcoming mobile application that will let DeFi investors utilize Limit Orders on any Decentralized Exchange (such as UniSwap or PancakeSwap)
- Limit Orders are sophisticated, and allow you set Tailing Stop Loss and Take Gains.
- ChillieWallet will be scalable to ANY blockchain that is based on Ethereum Virtual Machine.
- ChillieWallet will allow the user to import ANY Decentralized Exchange that is based around UniSwap's Router pattern.
- Chain your Limit Orders back to back, and let your Mobile Device be your personal day trader - according to the Trading Strategy that you Design! 
- Use of ChillieWallet means you can set your strategy, and STOP staring at charts all day!

- Check out the White Paper for basic information on ChillieWallet, and the underlying utility token: [https://github.com/iChillieman/ChillieWallet/blob/master/ChilliePaper_v1.pdf](https://github.com/iChillieman/ChillieWallet/blob/main/ChilliePaper.pdf)

Help us grow the community if the application sounds like something you would want to use!

Community:
 - [🌍 Website](https://chillieman.com) 
 - [👾 Discord](https://discord.gg/H8mbmDyYcz)
 - [ಠ_ಠ Reddit](https://www.reddit.com/r/ChillieWallet)
 - [💬 Telegram](https://t.me/OfficialChillieWallet)
 - [🐦 Twitter](https://twitter.com/ChillieWallet)
 - [🤖 GitHub](https://github.com/iChillieman)
 - [📺 Youtube](https://www.youtube.com/channel/UCS4C5tlb3U5R4ZpO_QDJL-Q)
