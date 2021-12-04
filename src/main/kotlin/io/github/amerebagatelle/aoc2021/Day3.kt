package io.github.amerebagatelle.aoc2021

class Day3: Puzzle {
    override fun getDay(): Int = 3

    override fun run1(): Number {
        val input = getInput()
        val gammaArray = "000000000000".toCharArray()

        repeat(input[0].length) {
            var count = 0
            for(line in input) {
                if(line[it] == '1') count++
            }
            if(count >= input.size/2) {
                gammaArray[it] = '1'
            }
        }
        val gamma = gammaArray.concatToString().toUInt(2)
        val epilson = gammaArray.map { if(it == '0') '1' else '0' }.toCharArray().concatToString().toUInt(2)

        return (gamma * epilson).toInt()
    }

    override fun run2(): Number {
        val input = getInput()
        val inputClone = input.toMutableList()

        for (i in 0..input[0].length) {
            var count = 0
            for(line in inputClone) if(line[i] == '0') count++

            val len = inputClone.size/2
            inputClone.removeAll { it[i] == if(count > len) '1' else '0' }
            if(inputClone.size <= 1) break
        }

        val oxygen = inputClone[0].toInt(2)

        val inputClone2 = input.toMutableList()

        for (i in 0..input[0].length) {
            var count = 0
            for(line in inputClone2) if(line[i] == '0') count++

            val len = inputClone2.size/2
            inputClone2.removeAll { it[i] == if(count <= len) '1' else '0' }
            if(inputClone2.size <= 1) break
        }

        val scrubber = inputClone2[0].toInt(2)

        return oxygen * scrubber
    }

}