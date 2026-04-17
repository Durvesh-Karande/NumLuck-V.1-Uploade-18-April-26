package com.example.numluck

object NumerologyInterpretations {

    fun numberMeaning(type: String, value: Int): String {
        val base = when (type) {
            "Life Path" -> lifePathMeaning(value)
            "Destiny" -> destinyMeaning(value)
            "Soul Urge" -> soulUrgeMeaning(value)
            "Personality" -> personalityMeaning(value)
            "Birthday" -> birthdayMeaning(value)
            "Maturity" -> maturityMeaning(value)
            "Personal Year" -> personalYearMeaning(value)
            else -> genericNumberMeaning(value)
        }
        val calc = calculationNote(type)
        return "$calc\n\n$base"
    }

    private fun calculationNote(type: String): String = when (type) {
        "Life Path" -> "Why this number today:\nYour Life Path Number is derived from the sum of your day, month, and year of birth, reduced to a single digit (keeping master numbers 11, 22, 33). It represents the core journey your soul has chosen for this lifetime."
        "Destiny" -> "Why this number today:\nYour Destiny Number is calculated from every letter in your full name. Each letter has a numerological value (A=1, B=2 … I=9, then repeating). Together they reveal the potential you are here to fulfil."
        "Soul Urge" -> "Why this number today:\nYour Soul Urge Number comes from the vowels in your name. Vowels speak to the heart — they describe what you truly long for beneath outer goals."
        "Personality" -> "Why this number today:\nYour Personality Number is formed from the consonants in your name. Consonants shape the outer mask — how the world first experiences you."
        "Birthday" -> "Why this number today:\nYour Birthday Number is simply the day you were born, reduced to a single digit. It uncovers a specific natural talent you carry."
        "Maturity" -> "Why this number today:\nYour Maturity Number blends your Life Path and Destiny into one vibration. It emerges most strongly after age 35 — the true self you are growing toward."
        "Personal Year" -> "Why this number today:\nYour Personal Year Number combines your day and month of birth with the current year. It describes the theme and energy of the year you are living right now."
        else -> "Why this number today:\nThis number was calculated from your personal details using classical numerology."
    }

    private fun lifePathMeaning(v: Int): String = when (v) {
        1 -> "Life Path 1 — The Pioneer. Independent, ambitious and a natural leader. You are here to originate, not imitate."
        2 -> "Life Path 2 — The Peacemaker. Sensitive, diplomatic, and cooperative. Your gift is bringing balance where there is tension."
        3 -> "Life Path 3 — The Communicator. Creative, expressive and joyful. Words, art and inspiration flow through you."
        4 -> "Life Path 4 — The Builder. Disciplined, loyal and practical. You are here to create something solid that lasts."
        5 -> "Life Path 5 — The Free Spirit. Curious, adaptable, and adventurous. Change and experience are your teachers."
        6 -> "Life Path 6 — The Nurturer. Responsible, loving and protective. Home, family and service call to you."
        7 -> "Life Path 7 — The Seeker. Thoughtful, spiritual and analytical. Truth lies for you in the inner world."
        8 -> "Life Path 8 — The Achiever. Powerful, business-minded and determined. You are meant to master the material world with integrity."
        9 -> "Life Path 9 — The Humanitarian. Compassionate, wise and idealistic. You serve a purpose larger than yourself."
        11 -> "Life Path 11 — The Intuitive (Master Number). Highly sensitive and spiritually attuned. You inspire others simply by being."
        22 -> "Life Path 22 — The Master Builder. You can turn grand visions into real-world structures that benefit many."
        33 -> "Life Path 33 — The Master Teacher. Healing and unconditional love flow through your work."
        else -> "Every Life Path carries its own gifts and lessons."
    }

    private fun destinyMeaning(v: Int): String = when (v) {
        1 -> "Destiny 1 — You are destined to lead, initiate and stand on your own."
        2 -> "Destiny 2 — Your destiny is about partnership, sensitivity and harmony."
        3 -> "Destiny 3 — Your destiny is expression: speaking, writing, creating, uplifting."
        4 -> "Destiny 4 — You are destined to build reliable structures others can rely on."
        5 -> "Destiny 5 — Freedom, travel, and versatility shape your destiny."
        6 -> "Destiny 6 — You are destined to care, heal and hold families or communities together."
        7 -> "Destiny 7 — Your destiny is the search for wisdom and hidden truth."
        8 -> "Destiny 8 — Material mastery, leadership, and legacy are your destiny."
        9 -> "Destiny 9 — Your destiny is to serve humanity and let go of what no longer serves."
        11 -> "Destiny 11 — A spiritual messenger destiny. Trust your inner voice."
        22 -> "Destiny 22 — Manifest something great that benefits the world."
        33 -> "Destiny 33 — Teach, heal, and love without condition."
        else -> "Your destiny is uniquely yours."
    }

    private fun soulUrgeMeaning(v: Int): String = when (v) {
        1 -> "Soul Urge 1 — Deep down you long to be independent and original."
        2 -> "Soul Urge 2 — You long for peace, companionship and emotional connection."
        3 -> "Soul Urge 3 — You long to express yourself and to feel joy."
        4 -> "Soul Urge 4 — You long for order, security and meaningful work."
        5 -> "Soul Urge 5 — You long for freedom, variety and adventure."
        6 -> "Soul Urge 6 — You long to love and be loved in a home you call your own."
        7 -> "Soul Urge 7 — You long for solitude, reflection and deeper meaning."
        8 -> "Soul Urge 8 — You long for achievement, respect and material success."
        9 -> "Soul Urge 9 — You long to make the world better and to be of service."
        11 -> "Soul Urge 11 — You long to inspire and to live a spiritually meaningful life."
        22 -> "Soul Urge 22 — You long to build something that outlasts you."
        33 -> "Soul Urge 33 — You long to uplift and heal those around you."
        else -> "Your heart has its own private language."
    }

    private fun personalityMeaning(v: Int): String = when (v) {
        1 -> "Personality 1 — Others see you as confident, independent and a leader."
        2 -> "Personality 2 — Others see you as gentle, kind and easy to talk to."
        3 -> "Personality 3 — Others see you as charming, witty and expressive."
        4 -> "Personality 4 — Others see you as grounded, reliable and hard-working."
        5 -> "Personality 5 — Others see you as exciting, free-spirited and adaptable."
        6 -> "Personality 6 — Others see you as caring, warm and responsible."
        7 -> "Personality 7 — Others see you as wise, mysterious and thoughtful."
        8 -> "Personality 8 — Others see you as strong, successful and in control."
        9 -> "Personality 9 — Others see you as compassionate, graceful and worldly."
        11 -> "Personality 11 — Others sense an intuitive, almost magnetic presence in you."
        22 -> "Personality 22 — Others see you as capable of building something remarkable."
        33 -> "Personality 33 — Others see you as a natural healer and teacher."
        else -> "Every personality leaves its own imprint."
    }

    private fun birthdayMeaning(v: Int): String = when (v) {
        1 -> "Birthday 1 — You arrived with the gift of leadership and originality."
        2 -> "Birthday 2 — You arrived with sensitivity and a gift for harmony."
        3 -> "Birthday 3 — You arrived with creativity and the joy of expression."
        4 -> "Birthday 4 — You arrived with discipline and the gift of building."
        5 -> "Birthday 5 — You arrived with curiosity and a love of freedom."
        6 -> "Birthday 6 — You arrived with a caring heart and a gift for nurturing."
        7 -> "Birthday 7 — You arrived with a thoughtful mind and a love of truth."
        8 -> "Birthday 8 — You arrived with strength and a gift for manifestation."
        9 -> "Birthday 9 — You arrived with compassion and a wide, generous heart."
        else -> "Your birth day carries a gift only you can bring."
    }

    private fun maturityMeaning(v: Int): String = when (v) {
        1 -> "Maturity 1 — In your later years you grow into a confident, self-reliant leader."
        2 -> "Maturity 2 — You grow into a wise peacemaker valued for emotional depth."
        3 -> "Maturity 3 — You grow into an expressive soul who brings joy wherever you go."
        4 -> "Maturity 4 — You grow into a dependable elder others build their lives around."
        5 -> "Maturity 5 — You grow into a free-thinking spirit with rich life stories."
        6 -> "Maturity 6 — You grow into a loving guardian of family and community."
        7 -> "Maturity 7 — You grow into a wise sage with deep inner understanding."
        8 -> "Maturity 8 — You grow into a respected figure of authority and abundance."
        9 -> "Maturity 9 — You grow into a compassionate elder who gives back freely."
        11 -> "Maturity 11 — You grow into a spiritual guide for those around you."
        22 -> "Maturity 22 — You grow into a builder whose work quietly changes lives."
        33 -> "Maturity 33 — You grow into a healer whose love uplifts many."
        else -> "Maturity is the quiet harvest of everything you have lived."
    }

    private fun personalYearMeaning(v: Int): String = when (v) {
        1 -> "Personal Year 1 — A year of new beginnings. Plant seeds boldly; you are starting a fresh nine-year cycle."
        2 -> "Personal Year 2 — A year of patience, partnership and cooperation. Relationships come to the foreground."
        3 -> "Personal Year 3 — A year of creativity, joy and social expansion. Let yourself shine."
        4 -> "Personal Year 4 — A year of steady work and foundation-building. Discipline pays off."
        5 -> "Personal Year 5 — A year of change, travel and freedom. Embrace the unexpected."
        6 -> "Personal Year 6 — A year focused on love, family and responsibility. Home takes centre stage."
        7 -> "Personal Year 7 — A year of reflection, study and spiritual growth. Go inward."
        8 -> "Personal Year 8 — A year of material reward and professional power. Take charge."
        9 -> "Personal Year 9 — A year of completion. Release what no longer belongs before the next cycle begins."
        11 -> "Personal Year 11 — A spiritually charged year. Intuition is unusually strong."
        22 -> "Personal Year 22 — A year to turn a grand vision into reality."
        33 -> "Personal Year 33 — A year to serve, teach or heal others."
        else -> "Every year carries its own energy."
    }

    private fun genericNumberMeaning(v: Int): String =
        "The vibration of $v carries unique qualities across every area of life."

    fun dateMeaning(date: Int, lifePath: Int): String {
        return """
            Why this date is lucky for you today:

            Your Life Path Number is $lifePath. In numerology, a calendar date is lucky when its digits reduce to your Life Path Number — meaning the date's vibration aligns with your soul's path.

            $date reduces to $lifePath. On days like this, your natural energy is amplified. Important decisions, new starts, interviews, travel and meaningful conversations tend to unfold more smoothly.

            How to use it:
            • Begin something you've been postponing
            • Schedule important meetings or signings
            • Travel, move or make a commitment
            • Reach out to someone who matters
        """.trimIndent()
    }

    fun weekdayMeaning(day: String, lifePath: Int): String {
        val ruler = when (day.lowercase()) {
            "sunday" -> "the Sun — identity, vitality, leadership"
            "monday" -> "the Moon — intuition, emotion, home"
            "tuesday" -> "Mars — courage, action, drive"
            "wednesday" -> "Mercury — communication, travel, learning"
            "thursday" -> "Jupiter — expansion, wisdom, good fortune"
            "friday" -> "Venus — love, beauty, harmony"
            "saturday" -> "Saturn — discipline, structure, responsibility"
            else -> "a planetary vibration unique to it"
        }
        return """
            Why $day is lucky for you:

            Your Life Path Number is $lifePath. In traditional numerology each weekday is ruled by a planet with a matching number, and $day is ruled by $ruler.

            This planetary energy resonates with your Life Path $lifePath, so on $day your natural gifts flow more easily and the universe feels a little more on your side.

            Best things to do on $day:
            • Act on ideas tied to your Life Path strengths
            • Start meaningful projects
            • Make important calls, send important messages
            • Spend time with people who uplift you
        """.trimIndent()
    }

    fun colorMeaning(color: String, lifePath: Int): String {
        val trait = when (color.lowercase()) {
            "gold" -> "confidence, success and solar vitality"
            "orange" -> "creativity, warmth and social energy"
            "white" -> "purity, clarity and fresh starts"
            "silver" -> "intuition, reflection and feminine energy"
            "rose pink" -> "love, compassion and gentle self-worth"
            "purple" -> "spiritual insight and transformation"
            "emerald green" -> "growth, healing and abundance"
            "blue" -> "calm, focus and honest communication"
            "gray" -> "neutrality, balance and versatility"
            "royal blue" -> "wisdom, loyalty and inner power"
            "indigo" -> "deep intuition and mystical insight"
            "maroon" -> "grounded strength and quiet confidence"
            "brown" -> "stability, earthiness and reliability"
            "navy" -> "discipline, authority and focused ambition"
            "black" -> "protection, power and self-possession"
            "red" -> "passion, courage and life force"
            "violet" -> "spiritual wisdom and higher awareness"
            else -> "energy that aligns with your personal vibration"
        }
        return """
            Why $color is lucky for you:

            Your Life Path Number is $lifePath. In numerology, colors carry their own vibration, and $color carries $trait — qualities that resonate with the gifts of Life Path $lifePath.

            Wearing or surrounding yourself with $color strengthens the side of you that your numbers already amplify.

            How to use it today:
            • Wear something in this color
            • Add it to your phone wallpaper, journal or workspace
            • Notice it when it appears around you — it is a small nudge from the universe
        """.trimIndent()
    }
}