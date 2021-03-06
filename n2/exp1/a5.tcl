set ns [ new Simulator ]
set tf [ open lab1e.tr w ]
$ns trace-all $tf

set nf [ open lab1e.nam w ]
$ns namtrace-all $nf

set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]

$ns color 1 "red"
$ns color 2 "blue"
$ns color 3 "green"

$n0 label "Source"
$n1 label "Router"
$n2 label "Reciever"

$ns duplex-link $n0 $n1 1Mb 10ms DropTail
$ns duplex-link $n1 $n2 0.6Mb 10ms DropTail

$ns set queue-limt $n0 $n1 20
$ns set queue-limit $n0 $n1 20

set udp0 [new Agent/UDP]
$ns attach-agent $n0 $udp0
set cbr0 [new Application/Traffic/CBR]
$cbr0 attach-agent $udp0

set null1 [new Agent/Null]
$ns attach-agent $n2 $null1

$udp0 set class_ 1

$ns connect $udp0 $null1

$cbr0 set packetSize_ 100KB
$cbr0 set interval_ 0.001

proc finish {} {
	global ns nf tf
	$ns flush-trace
	exec nam lab1e.nam &
	close $tf
	close $nf
	exit 0
}

$ns at 0.1 "$cbr0 start"
$ns at 2.1 "$cbr0 stop"

$ns at 3.0 "finish"

$ns run

