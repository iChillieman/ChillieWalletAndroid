package com.chillieman.chilliewallet.model.contracts;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class ChillieWalletContract extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5060405161122b38038061122b83398101604081905261002f916100d7565b600080546001600160a01b0319166001600160a01b03831690811790915560408051630b4a282f60e11b81529051631694505e916004808201926020929091908290030181865afa158015610088573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906100ac91906100d7565b6001805460006002556001600160a81b0319166001600160a01b039290921691909117905550610107565b6000602082840312156100e957600080fd5b81516001600160a01b038116811461010057600080fd5b9392505050565b611115806101166000396000f3fe6080604052600436106100ab5760003560e01c8063bbd9cee911610064578063bbd9cee91461017b578063c1f2df9e146101c4578063d5bfc830146101d9578063de65ca4614610209578063e1cda2eb1461021c578063efaa975e1461023b57600080fd5b80631694505e146100b7578063179229f1146100ee5780635c74c7461461010c5780637e1dad841461012a5780639b5415141461013f578063a61849401461015457600080fd5b366100b257005b600080fd5b3480156100c357600080fd5b506001546001600160a01b03165b6040516001600160a01b0390911681526020015b60405180910390f35b3480156100fa57600080fd5b506002546040519081526020016100e5565b34801561011857600080fd5b506000546001600160a01b03166100d1565b61013d610138366004610f3f565b61025b565b005b34801561014b57600080fd5b5061013d61053d565b34801561016057600080fd5b5073775e3bbfb07496db8ed33a86df0e41345f11ea216100d1565b34801561018757600080fd5b506101b4610196366004610f76565b6001600160a01b031660009081526004602052604090205460ff1690565b60405190151581526020016100e5565b3480156101d057600080fd5b5061013d610682565b3480156101e557600080fd5b506101b46101f4366004610f93565b60009081526006602052604090205460ff1690565b61013d610217366004610f93565b610785565b34801561022857600080fd5b50600154600160a01b900460ff166101b4565b34801561024757600080fd5b5061013d610256366004610f93565b610c2c565b600154600160a01b900460ff1661028d5760405162461bcd60e51b815260040161028490610fac565b60405180910390fd5b3360009081526004602052604090205460ff166102ec5760405162461bcd60e51b815260206004820152601e60248201527f57616c6c65742055736572206973206e6f7420417574686f72697a65642100006044820152606401610284565b60008181526006602052604090205460ff161561035b5760405162461bcd60e51b815260206004820152602760248201527f5472616e73616374696f6e2068617320616c7265616479206265656e20706179604482015266656420666f722160c81b6064820152608401610284565b600082116103b65760405162461bcd60e51b815260206004820152602260248201527f5472616e73616374696f6e20416d6f756e742063616e6e6f74206265207a65726044820152616f2160f01b6064820152608401610284565b8134146104245760405162461bcd60e51b815260206004820152603660248201527f5472616e73616374696f6e20416d6f756e7420697320646966666572656e74206044820152757468616e20746865204554482050726f76696465642160501b6064820152608401610284565b600160005b60055481101561048557336001600160a01b03166005828154811061045057610450610ff3565b6000918252602090912001546001600160a01b0316036104735760009150610485565b8061047d81611009565b915050610429565b5080156104cf57600580546001810182556000919091527f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db00180546001600160a01b031916331790555b6000828152600660205260409020805460ff191660011790557fdd32adbffb5221d4a7b62ddba35ae96615cc60c40fcf55fba1f2b271af241fc36105103390565b604080516001600160a01b03909216825260208201869052810184905260600160405180910390a1505050565b73775e3bbfb07496db8ed33a86df0e41345f11ea2133146105705760405162461bcd60e51b815260040161028490611030565b600154600160a01b900460ff166105db5760405162461bcd60e51b815260206004820152602960248201527f4368696c6c69652057616c6c6574206973206e6f742063757272656e746c792060448201526834b71039b2b0b9b7b760b91b6064820152608401610284565b60005b60035481101561063d5760046000600383815481106105ff576105ff610ff3565b60009182526020808320909101546001600160a01b031683528201929092526040019020805460ff191690558061063581611009565b9150506105de565b5061064a60036000610f05565b6001805460ff60a01b191690556040517f70bd1186b72128c7e57b88c663bf97860c6224c262917e5a863a7599474f0b8390600090a1565b73775e3bbfb07496db8ed33a86df0e41345f11ea2133146106b55760405162461bcd60e51b815260040161028490611030565b600047116107055760405162461bcd60e51b815260206004820152601a60248201527f4e6f2057616c6c657420546178657320746f2070726f636573730000000000006044820152606401610284565b73775e3bbfb07496db8ed33a86df0e41345f11ea216108fc610728600247611065565b6040518115909202916000818181858888f19350505050158015610750573d6000803e3d6000fd5b50610759610d11565b506040517ff8e3c93e8e3e747c41f3014952902989ed32b0570a0d046cf3af958c92a450f690600090a1565b600154600160a01b900460ff166107ae5760405162461bcd60e51b815260040161028490610fac565b3360009081526004602052604090205460ff16156108195760405162461bcd60e51b815260206004820152602260248201527f57616c6c6574205573657220697320616c726561647920417574686f72697a65604482015261642160f01b6064820152608401610284565b60025481146108855760405162461bcd60e51b815260206004820152603260248201527f5472616e73616374696f6e20416d6f756e74206d757374206265207468652073604482015271616d6520617320536561736f6e204665652160701b6064820152608401610284565b8015610b935760005481906001600160a01b031663dd62ed3e336040516001600160e01b031960e084901b1681526001600160a01b039091166004820152306024820152604401602060405180830381865afa1580156108e9573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061090d9190611087565b10156109bb5760405162461bcd60e51b815260206004820152606b60248201527f596f75206e65656420746f2061646420616c6c6f77616e636520736f2043686960448201527f6c6c69652057616c6c65742063616e2075736520796f757220746f6b656e732e60648201527f202d20546869732073686f756c642062652074616b656e2063617265206f662060848201526a313c903a34329020b8381760a91b60a482015260c401610284565b60005481906001600160a01b03166370a08231336040516001600160e01b031960e084901b1681526001600160a01b039091166004820152602401602060405180830381865afa158015610a13573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610a379190611087565b11610a985760405162461bcd60e51b815260206004820152602b60248201527f57616c6c6574205573657220646f65736e74206861766520746865207265717560448201526a6972656420546f6b656e7360a81b6064820152608401610284565b6000546001600160a01b03166323b872dd336040516001600160e01b031960e084901b1681526001600160a01b039091166004820152306024820152604481018490526064016020604051808303816000875af1158015610afd573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610b2191906110a0565b506000546040516317ce970d60e31b8152600481018390526001600160a01b039091169063be74b868906024016020604051808303816000875af1158015610b6d573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610b9191906110a0565b505b6003805460018082019092557fc2575a0e9e593c00f959f8c92f12db2869c3395a3b0502d05e2516446f71f85b0180546001600160a01b03191633908117909155600081815260046020908152604091829020805460ff191690941790935580519182529181018390527fedbe5371a318fedac93cc72b279a63ae2c59ecd8bcf36a936d56b3957f105f8691015b60405180910390a150565b73775e3bbfb07496db8ed33a86df0e41345f11ea213314610c5f5760405162461bcd60e51b815260040161028490611030565b600154600160a01b900460ff1615610cc75760405162461bcd60e51b815260206004820152602560248201527f4368696c6c69652057616c6c657420697320616c726561647920696e2061207360448201526432b0b9b7b760d91b6064820152608401610284565b60028190556001805460ff60a01b1916600160a01b1790556040517fe76cc4949eeae41ebb1538e4f937fcad8d78a0e13eef6fbbaaae6116e31a359390610c219083815260200190565b600080610d27610d22600247611065565b610da8565b600054604051636952b41f60e11b8152600481018390524760248201529192506001600160a01b03169063d2a5683e90479060440160206040518083038185885af1158015610d7a573d6000803e3d6000fd5b50505050506040513d601f19601f82011682018060405250810190610d9f91906110a0565b50600191505090565b604080516002808252606082018352600092839291906020830190803683375050600154604080516315ab88c960e31b815290519394506001600160a01b039091169263ad5c4648925060048083019260209291908290030181865afa158015610e16573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610e3a91906110c2565b81600081518110610e4d57610e4d610ff3565b60200260200101906001600160a01b031690816001600160a01b0316815250503081600181518110610e8157610e81610ff3565b6001600160a01b039283166020918202929092010152600054604051623199f960e31b81526004810186905291169063018ccfc890859060240160206040518083038185885af1158015610ed9573d6000803e3d6000fd5b50505050506040513d601f19601f82011682018060405250810190610efe9190611087565b9392505050565b5080546000825590600052602060002090810190610f239190610f26565b50565b5b80821115610f3b5760008155600101610f27565b5090565b60008060408385031215610f5257600080fd5b50508035926020909101359150565b6001600160a01b0381168114610f2357600080fd5b600060208284031215610f8857600080fd5b8135610efe81610f61565b600060208284031215610fa557600080fd5b5035919050565b60208082526027908201527f4368696c6c69652057616c6c6574206973206e6f742063757272656e746c7920604082015266195b98589b195960ca1b606082015260800190565b634e487b7160e01b600052603260045260246000fd5b60006001820161102957634e487b7160e01b600052601160045260246000fd5b5060010190565b6020808252818101527f44656e6965643a2063616c6c6572206973206e6f74204368696c6c69656d616e604082015260600190565b60008261108257634e487b7160e01b600052601260045260246000fd5b500490565b60006020828403121561109957600080fd5b5051919050565b6000602082840312156110b257600080fd5b81518015158114610efe57600080fd5b6000602082840312156110d457600080fd5b8151610efe81610f6156fea2646970667358221220ac54714d5dc60e5f3adf4fda1c4b816a4367d07deb9a5d17ca0b86e33af95aeb64736f6c634300080f0033";

    public static final String FUNC_ACTIVATEACCOUNTFORCURRENTCHILLIEWALLETSEASON = "activateAccountForCurrentChillieWalletSeason";

    public static final String FUNC_CHILLIEENDWALLETSEASON = "chillieEndWalletSeason";

    public static final String FUNC_CHILLIEPROCESSWALLETTAXES = "chillieProcessWalletTaxes";

    public static final String FUNC_CHILLIESTARTWALLETSEASON = "chillieStartWalletSeason";

    public static final String FUNC_CHILLIETOKEN = "chillieToken";

    public static final String FUNC_CHILLIEMAN = "chillieman";

    public static final String FUNC_GETCHILLIEWALLETSEASONFEE = "getChillieWalletSeasonFee";

    public static final String FUNC_ISAUTHORIZEDTOUSEWALLET = "isAuthorizedToUseWallet";

    public static final String FUNC_ISCHILLIEWALLETSEASONACTIVE = "isChillieWalletSeasonActive";

    public static final String FUNC_ISTRANSACTIONPAYEDFOR = "isTransactionPayedFor";

    public static final String FUNC_PAYCHILLIEWALLETTAX = "payChillieWalletTax";

    public static final String FUNC_UNISWAPV2ROUTER = "uniswapV2Router";

    public static final Event CHILLIEWALLETSEASONENDED_EVENT = new Event("ChillieWalletSeasonEnded", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event CHILLIEWALLETSEASONSTARTED_EVENT = new Event("ChillieWalletSeasonStarted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event CHILLIEWALLETTAXESPROCESSED_EVENT = new Event("ChillieWalletTaxesProcessed", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event PAIDWALLETSEASONFEES_EVENT = new Event("PaidWalletSeasonFees", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event PAIDWALLETTAXES_EVENT = new Event("PaidWalletTaxes", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bytes32>() {}));
    ;

    @Deprecated
    protected ChillieWalletContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ChillieWalletContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ChillieWalletContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ChillieWalletContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<ChillieWalletSeasonEndedEventResponse> getChillieWalletSeasonEndedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CHILLIEWALLETSEASONENDED_EVENT, transactionReceipt);
        ArrayList<ChillieWalletSeasonEndedEventResponse> responses = new ArrayList<ChillieWalletSeasonEndedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ChillieWalletSeasonEndedEventResponse typedResponse = new ChillieWalletSeasonEndedEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ChillieWalletSeasonEndedEventResponse> chillieWalletSeasonEndedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ChillieWalletSeasonEndedEventResponse>() {
            @Override
            public ChillieWalletSeasonEndedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CHILLIEWALLETSEASONENDED_EVENT, log);
                ChillieWalletSeasonEndedEventResponse typedResponse = new ChillieWalletSeasonEndedEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public Flowable<ChillieWalletSeasonEndedEventResponse> chillieWalletSeasonEndedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CHILLIEWALLETSEASONENDED_EVENT));
        return chillieWalletSeasonEndedEventFlowable(filter);
    }

    public List<ChillieWalletSeasonStartedEventResponse> getChillieWalletSeasonStartedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CHILLIEWALLETSEASONSTARTED_EVENT, transactionReceipt);
        ArrayList<ChillieWalletSeasonStartedEventResponse> responses = new ArrayList<ChillieWalletSeasonStartedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ChillieWalletSeasonStartedEventResponse typedResponse = new ChillieWalletSeasonStartedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.entryAmount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ChillieWalletSeasonStartedEventResponse> chillieWalletSeasonStartedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ChillieWalletSeasonStartedEventResponse>() {
            @Override
            public ChillieWalletSeasonStartedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CHILLIEWALLETSEASONSTARTED_EVENT, log);
                ChillieWalletSeasonStartedEventResponse typedResponse = new ChillieWalletSeasonStartedEventResponse();
                typedResponse.log = log;
                typedResponse.entryAmount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ChillieWalletSeasonStartedEventResponse> chillieWalletSeasonStartedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CHILLIEWALLETSEASONSTARTED_EVENT));
        return chillieWalletSeasonStartedEventFlowable(filter);
    }

    public List<ChillieWalletTaxesProcessedEventResponse> getChillieWalletTaxesProcessedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CHILLIEWALLETTAXESPROCESSED_EVENT, transactionReceipt);
        ArrayList<ChillieWalletTaxesProcessedEventResponse> responses = new ArrayList<ChillieWalletTaxesProcessedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ChillieWalletTaxesProcessedEventResponse typedResponse = new ChillieWalletTaxesProcessedEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ChillieWalletTaxesProcessedEventResponse> chillieWalletTaxesProcessedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ChillieWalletTaxesProcessedEventResponse>() {
            @Override
            public ChillieWalletTaxesProcessedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CHILLIEWALLETTAXESPROCESSED_EVENT, log);
                ChillieWalletTaxesProcessedEventResponse typedResponse = new ChillieWalletTaxesProcessedEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public Flowable<ChillieWalletTaxesProcessedEventResponse> chillieWalletTaxesProcessedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CHILLIEWALLETTAXESPROCESSED_EVENT));
        return chillieWalletTaxesProcessedEventFlowable(filter);
    }

    public List<PaidWalletSeasonFeesEventResponse> getPaidWalletSeasonFeesEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PAIDWALLETSEASONFEES_EVENT, transactionReceipt);
        ArrayList<PaidWalletSeasonFeesEventResponse> responses = new ArrayList<PaidWalletSeasonFeesEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PaidWalletSeasonFeesEventResponse typedResponse = new PaidWalletSeasonFeesEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.walletUser = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PaidWalletSeasonFeesEventResponse> paidWalletSeasonFeesEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, PaidWalletSeasonFeesEventResponse>() {
            @Override
            public PaidWalletSeasonFeesEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PAIDWALLETSEASONFEES_EVENT, log);
                PaidWalletSeasonFeesEventResponse typedResponse = new PaidWalletSeasonFeesEventResponse();
                typedResponse.log = log;
                typedResponse.walletUser = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PaidWalletSeasonFeesEventResponse> paidWalletSeasonFeesEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PAIDWALLETSEASONFEES_EVENT));
        return paidWalletSeasonFeesEventFlowable(filter);
    }

    public List<PaidWalletTaxesEventResponse> getPaidWalletTaxesEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PAIDWALLETTAXES_EVENT, transactionReceipt);
        ArrayList<PaidWalletTaxesEventResponse> responses = new ArrayList<PaidWalletTaxesEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PaidWalletTaxesEventResponse typedResponse = new PaidWalletTaxesEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.walletUser = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.transaction = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PaidWalletTaxesEventResponse> paidWalletTaxesEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, PaidWalletTaxesEventResponse>() {
            @Override
            public PaidWalletTaxesEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PAIDWALLETTAXES_EVENT, log);
                PaidWalletTaxesEventResponse typedResponse = new PaidWalletTaxesEventResponse();
                typedResponse.log = log;
                typedResponse.walletUser = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.transaction = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PaidWalletTaxesEventResponse> paidWalletTaxesEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PAIDWALLETTAXES_EVENT));
        return paidWalletTaxesEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> activateAccountForCurrentChillieWalletSeason(BigInteger amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ACTIVATEACCOUNTFORCURRENTCHILLIEWALLETSEASON, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> chillieEndWalletSeason() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CHILLIEENDWALLETSEASON, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> chillieProcessWalletTaxes() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CHILLIEPROCESSWALLETTAXES, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> chillieStartWalletSeason(BigInteger seasonFee) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CHILLIESTARTWALLETSEASON, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(seasonFee)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> chillieToken() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CHILLIETOKEN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> chillieman() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CHILLIEMAN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> getChillieWalletSeasonFee() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETCHILLIEWALLETSEASONFEE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> isAuthorizedToUseWallet(String walletUser) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISAUTHORIZEDTOUSEWALLET, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, walletUser)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isChillieWalletSeasonActive() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISCHILLIEWALLETSEASONACTIVE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isTransactionPayedFor(byte[] transaction) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISTRANSACTIONPAYEDFOR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(transaction)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> payChillieWalletTax(BigInteger amount, byte[] transaction) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_PAYCHILLIEWALLETTAX, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.web3j.abi.datatypes.generated.Bytes32(transaction)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> uniswapV2Router() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_UNISWAPV2ROUTER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static ChillieWalletContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ChillieWalletContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ChillieWalletContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ChillieWalletContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ChillieWalletContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ChillieWalletContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ChillieWalletContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ChillieWalletContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ChillieWalletContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String chillieTokenAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, chillieTokenAddress)));
        return deployRemoteCall(ChillieWalletContract.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<ChillieWalletContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String chillieTokenAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, chillieTokenAddress)));
        return deployRemoteCall(ChillieWalletContract.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<ChillieWalletContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String chillieTokenAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, chillieTokenAddress)));
        return deployRemoteCall(ChillieWalletContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<ChillieWalletContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String chillieTokenAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, chillieTokenAddress)));
        return deployRemoteCall(ChillieWalletContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class ChillieWalletSeasonEndedEventResponse extends BaseEventResponse {
    }

    public static class ChillieWalletSeasonStartedEventResponse extends BaseEventResponse {
        public BigInteger entryAmount;
    }

    public static class ChillieWalletTaxesProcessedEventResponse extends BaseEventResponse {
    }

    public static class PaidWalletSeasonFeesEventResponse extends BaseEventResponse {
        public String walletUser;

        public BigInteger amount;
    }

    public static class PaidWalletTaxesEventResponse extends BaseEventResponse {
        public String walletUser;

        public BigInteger amount;

        public byte[] transaction;
    }
}
