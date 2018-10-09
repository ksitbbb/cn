set ns [ new Simulator ]
set tf [ open lab3a.tr w ]
$ns trace-all $tf

set nf [ open lab3a.nam w ]
$ns namtrace-all $nf

set file1 [ open conn1.tr w ]
set file2 [ open conn2.tr w ]

set n0 [ $ns node ]
set n1 [ $ns node ]
set n2 [ $ns node ]
set n3 [ $ns node ]
set n4 [ $ns node ]

$ns color 1 "red"
$ns color 2 "green"
$ns color 3 "blue"
$ns color 4 "purple"
$ns color 5 "violet"

$ns make-lan "$n0 $n1 $n2 $n3 $n4" 1Mb 10ms LL Queue/DropTail Mac/802_3
#$ns make-lan "$n0 $n1 $n2 $n3 $n4" 10Mb 10ms LL Queue/DropTail Mac/802_3

set tcp0 [new Agent/TCP]
$ns attach-agent $n0 $tcp0
set tcp1 [new Agent/TCP/Reno]
$ns attach-agent $n1 $tcp1

set sink0 [new Agent/TCPSink]
set sink1 [new Agent/TCPSink]
$ns attach-agent $n2 $sink0
$ns attach-agent $n3 $sink1

Agent/TCP set packetSize_ 960


set ftp0 [new Application/FTP]
set ftp1 [new Application/FTP]
$ftp0 attach-agent $tcp0
$ftp1 attach-agent $tcp1

$ns connect $tcp0 $sink0
$ns connect $tcp1 $sink1

$tcp0 attach $file1
$tcp1 attach $file2

$tcp0 set class_ 1
$tcp1 set class_ 2


$tcp0 trace cwnd_
$tcp1 trace cwnd_

proc finish { } {
	global ns nf tf file1 file2
	$ns flush-trace
	close $tf
	close $nf
	close $file1
	close $file2
	exec nam lab3a.nam &
	exit 0
}

$ns at 1.0 "$ftp0 start"
$ns at 5.0 "$ftp0 stop"
$ns at 5.0 "$ftp1 start"
$ns at 9.0 "$ftp1 stop"
$ns at 10.5 "$ftp0 start"
$ns at 11.0 "$ftp1 start"
$ns at 19.0 "$ftp0 stop"
$ns at 20.0 "$ftp1 stop"


$ns at 21 finish
$ns run