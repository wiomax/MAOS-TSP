java -server -Xmx256M -jar ../release/MAOS_SEQ.jar TSP:Problem=rl1323 N=300 T=100 DUP_TIMES=5 solver=STD_PRB_3Opt_REAX+SEAX:isAlpha=true
java -server -Xmx256M -cp ../binary/MAOS_SEQ.jar maosKernel.MAOSExecuter TSP:Problem=a280   N=50
