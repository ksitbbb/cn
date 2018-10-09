set ns [ new Simulator ]
set tf [ open t1.tr w ]
$ns trace-all $tf

set nf [ open t1.nam w ]
$ns namtrace-all $nf

#set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]
#set n3 [$ns node]

$ns color 1 "red"
$ns color 2 "blue"
$n1 label "Source"
$n2 label "Reciever/Null"
#$n3 label "Mr. FBI"

$ns duplex-link $n1 $n2 20Mb 200ms DropTail

$ns set queue-limit $n1 $n2 50

set udp0 [new Agent/UDP]
$ns attach-agent $n1 $udp0
set cbr0 [new Application/Traffic/CBR]
$cbr0 attach-agent $udp0

set null3 [new Agent/Null]
$ns attach-agent $n2 $null3

$udp0 set class_ 1
$ns connect $udp0 $null3

$cbr0 set packetSize_ 500000
$cbr0 set interval_ 0.10

proc finish {} {
    global ns nf tf
    $ns flush-trace
    close $tf
    close $nf
    exec nam t1.nam &
    exit 0
}

$ns at 0.1 "$cbr0 start"

$ns at 5.0 "finish"

$ns run
