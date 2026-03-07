package com.alexyach.compose.groupsaa.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.LineHeightStyle.Alignment
import androidx.compose.ui.text.style.LineHeightStyle.Mode
import androidx.compose.ui.text.style.LineHeightStyle.Trim
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/* 3 Step*/
fun prayerDelegationText(
    titleSize: Int,
    textSize: Int
): AnnotatedString {
    return buildAnnotatedString {
        withStyle(ParagraphStyle(textAlign = TextAlign.Center)) {
            withStyle(SpanStyle(fontSize = titleSize.sp, fontWeight = FontWeight.Bold)) {
                append("Доручення")
            }
        }

        withStyle(
            ParagraphStyle(
                textAlign = TextAlign.Justify,
                textIndent = TextIndent(firstLine = 30.sp)
            )
        ) {
            withStyle(
                SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Bold)
            ) {
                append("Боже")
            }
            withStyle(SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Normal)) {
                append(
                    ", я віддаю себе Тобі – будуй мене і роби зі мною те, що Ти захочеш. " +
                            "Звільни мене від рабства власного «я», щоб я міг краще виконувати " +
                            "Твою волю. Забери мої труднощі, щоб перемога над ними була свідченням " +
                            "Твоєї Могутності, Твоєї Любові і Твого Способу життя для тих, кому я " +
                            "буду допомагати. Нехай я завжди виконуватиму Твою волю!"
                )
            }
        }
    }
}

/* ***************************** 11 Step. Morning ****************************  */
fun prayerElevenMorningText(
    titleSize: Int,
    textSize: Int
): AnnotatedString {
    return buildAnnotatedString {
        withStyle(ParagraphStyle(textAlign = TextAlign.Center)) {
            withStyle(SpanStyle(fontSize = titleSize.sp, fontWeight = FontWeight.Bold)) {
                append("11 Крок. Ранок")
            }
        }
        withStyle(ParagraphStyle(textAlign = TextAlign.Center)) {
            withStyle(SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Normal)) {
                append("Одинадцятий Крок пропонує молитву і медитацію")
            }
        }

        /* Space*/
//        withStyle(SpanStyle(fontSize = 4.sp)) { append(" ") }
        withStyle(ParagraphStyle(lineHeight = 4.sp)) {}

        withStyle(ParagraphStyle(textAlign = TextAlign.Start)) {
            withStyle(
                SpanStyle(
                    fontSize = textSize.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Normal,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append("Молитва на самому початку дня:")
            }
        }

        withStyle(
            ParagraphStyle(
                textAlign = TextAlign.Justify,
                textIndent = TextIndent(firstLine = 30.sp)
            )
        ) {
            withStyle(
                SpanStyle(
                    fontSize = textSize.sp,
//                fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Normal
                )
            ) {
                append(
                    "Боже, спрямуй мої помисли у вірне русло, убережи мене від жалю до " +
                            "себе, від безчесних вчинків, користолюбства."
                )
            }
        }

        /* Space*/
//        withStyle(SpanStyle(fontSize = 4.sp)) { append(" ") }
        withStyle(ParagraphStyle(lineHeight = 4.sp)) {}

        withStyle(
            ParagraphStyle(
                textAlign = TextAlign.Justify,
                textIndent = TextIndent(firstLine = 30.sp)
            )
        ) {
            withStyle(
                SpanStyle(
                    fontSize = textSize.sp,
                    fontWeight = FontWeight.Normal
                )
            ) {
                append(
                    "Думаючи про свій день, ми можемо зіткнутися з нерішучістю. Нам може бути " +
                            "важко визначити, який шлях обрати."
                )
            }
        }

        /* Space*/
//        withStyle(SpanStyle(fontSize = 4.sp)) { append(" ") }
        withStyle(ParagraphStyle(lineHeight = 4.sp)) {}

        withStyle(ParagraphStyle(textAlign = TextAlign.Start)) {
            withStyle(
                SpanStyle(
                    fontSize = textSize.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Normal,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append("Молитва:")
            }
        }
        withStyle(
            ParagraphStyle(
                textAlign = TextAlign.Justify,
                textIndent = TextIndent(firstLine = 30.sp)
            )
        ) {
            withStyle(SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Normal)) {
                append("Боже, даруй мені натхнення, інтуїтивної думки чи рішення.")
            }
        }

        /* Space*/
//        withStyle(SpanStyle(fontSize = 4.sp)) { append(" ") }
        withStyle(ParagraphStyle(lineHeight = 4.sp)) {}

        withStyle(
            ParagraphStyle(
                textAlign = TextAlign.Justify,
                textIndent = TextIndent(firstLine = 30.sp)
            )
        ) {
            withStyle(SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Normal)) {
                append("Ми розслаблюємося і не напружуємося. Ми не боремося.")
            }
        }
        /* Space*/
//        withStyle(SpanStyle(fontSize = 4.sp)) { append(" ") }
        withStyle(ParagraphStyle(lineHeight = 4.sp)) {}

        withStyle(ParagraphStyle(textAlign = TextAlign.Start)) {
            withStyle(
                SpanStyle(
                    fontSize = textSize.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Normal,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append("Молитва:")
            }
        }
        withStyle(
            ParagraphStyle(
                textAlign = TextAlign.Justify,
                textIndent = TextIndent(firstLine = 30.sp),
                lineHeight = TextUnit.Unspecified,
//            lineHeightStyle = LineHeightStyle
//                (alignment = Alignment.Proportional,
//                trim = Trim.None,
//                mode = Mode.Fixed)
//            lineBreak = LineBreak.Paragraph
            )
        ) {
            withStyle(SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Normal)) {
                append(
                    "Боже, покажі мені протягом дня, яким повинен бути мій наступний крок, і " +
                            "щоб мені було дано те, що потрібно для вирішення таких проблем. Звільни " +
                            "мене від свавілля."
                )
            }
        }

        /* Space*/
//        withStyle(SpanStyle(fontSize = 4.sp)) { append(" ") }
        withStyle(ParagraphStyle(lineHeight = 4.sp)) {}

        withStyle(
            ParagraphStyle(
                textAlign = TextAlign.Justify,
                textIndent = TextIndent(firstLine = 30.sp)
            )
        ) {
            withStyle(SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Normal)) {
                append(
                    "Впродовж дня, коли ми схвильовані або сумніваємося, ми зупиняємось і " +
                            "просимо про вірну думку чи дію. Ми постійно нагадуємо собі, що більше " +
                            "не керуємо виставою, покірно кажучи собі багато разів кожного дня: " +
                            "«Нехай буде воля Твоя»."
                )
            }
        }


        withStyle(
            ParagraphStyle(
                textAlign = TextAlign.Center,
            )
        ) {
            withStyle(SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Normal)) {
                append("*****")
            }
        }

        withStyle(
            ParagraphStyle(
                textAlign = TextAlign.Justify,
                textIndent = TextIndent(firstLine = 30.sp)
            )
        ) {
            withStyle(SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Normal)) {
                append(
                    "Боже, даруй мені душевний спокій, щоб не бентежитись за те, чого я не в силах змінити. " +
                            "Мужністю наділи, щоб змінювати в собі те що заважає служити Тобі і іншим. " +
                            "Смиренно молю за здоровий розум, щоб виконувати волю Твою, а не свою. Амінь."
                )
            }
        }

        /* Space*/
//        withStyle(SpanStyle(fontSize = 4.sp)) { append(" ") }
        withStyle(ParagraphStyle(lineHeight = 4.sp)) {}

        withStyle(
            ParagraphStyle(
                textAlign = TextAlign.Justify,
                textIndent = TextIndent(firstLine = 30.sp)
            )
        ) {
            withStyle(SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Normal)) {
                append(
                    "Боже, направ думки мої по стопах Твоїх. Благаю, відведи від користолюбства яке" +
                            " штовхає мене на всяку несправедливість і змушує жити в страху."
                )
            }
        }

        /* Space*/
//        withStyle(SpanStyle(fontSize = 4.sp)) { append(" ") }
        withStyle(ParagraphStyle(lineHeight = 4.sp)) {}

        withStyle(
            ParagraphStyle(
                textAlign = TextAlign.Justify,
                textIndent = TextIndent(firstLine = 30.sp)
            )
        ) {
            withStyle(SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Normal)) {
                append(
                    "Смирення і спокою прошу в молитві. Щоб звільнитися від жалю до себе, хибних " +
                            "мотивів, та даруй інтуїтивні думки і рішення які потрібні щоб довірити" +
                            " Тобі свій сьогоднішній день."
                )
            }
        }


        /* Space*/
//        withStyle(SpanStyle(fontSize = 4.sp)) { append(" ") }.
        withStyle(ParagraphStyle(lineHeight = 4.sp)) {}

        withStyle(
            ParagraphStyle(
                textAlign = TextAlign.Justify,
                textIndent = TextIndent(firstLine = 30.sp)
            )
        ) {
            withStyle(SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Normal)) {
                append(
                    "Господи, подбай про мене будь ласка. Не залишай у хвилини сумнівів, ревнощів, " +
                            "люті і підкажи слушне слово і дію, щоб зрозуміти яким повинен бути " +
                            "мій наступний крок."
                )
            }
        }

        /* Space*/
//        withStyle(SpanStyle(fontSize = 4.sp)) { append(" ") }
        withStyle(ParagraphStyle(lineHeight = 4.sp)) {}

        withStyle(
            ParagraphStyle(
                textAlign = TextAlign.Justify,
                textIndent = TextIndent(firstLine = 30.sp)
            )
        ) {
            withStyle(SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Normal)) {
                append(
                    "Будь ласка, звільни мене від свавілля і егоїзму. Щоб розгледіти: в кожній " +
                            "людині- Твій образ, в кожному випадку- Твій промисел, в кожному дні" +
                            "- Твою безумовну любов до мене."
                )
            }
        }

        /* Space*/
//        withStyle(SpanStyle(fontSize = 4.sp)) { append(" ") }
        withStyle(ParagraphStyle(lineHeight = 4.sp)) {}

        withStyle(
            ParagraphStyle(
                textAlign = TextAlign.Justify,
                textIndent = TextIndent(firstLine = 30.sp)
            )
        ) {
            withStyle(SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Normal)) {
                append(
                    "В повній вірі молюся за ще один тверезий день - СЬОГОДНІ."
                )
            }
        }

        /* Space*/
//        withStyle(SpanStyle(fontSize = 4.sp)) { append(" ") }
        withStyle(ParagraphStyle(lineHeight = 4.sp)) {}

        withStyle(
            ParagraphStyle(
                textAlign = TextAlign.Justify,
                textIndent = TextIndent(firstLine = 30.sp)
            )
        ) {
            withStyle(SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Normal)) {
                append(
                    "Я виконаю волю Твою, дай мені сил для цього, а якщо зхиблю то помилуй мене," +
                            " Господи. Амінь."
                )
            }
        }


    }
}

/* ***************************** 11 Step. Evening ****************************  */
fun prayerElevenEveningText(
    titleSize: Int,
    textSize: Int
): AnnotatedString {
    return buildAnnotatedString {
        withStyle(ParagraphStyle(textAlign = TextAlign.Center)) {
            withStyle(SpanStyle(fontSize = titleSize.sp, fontWeight = FontWeight.Bold)) {
                append("11 Крок. Вечір")
            }
        }
        /* Space*/
//        withStyle(SpanStyle(fontSize = 4.sp)) { append(" ") }
//        withStyle(ParagraphStyle(lineHeight = 4.sp)) {}

        withStyle(ParagraphStyle(textAlign = TextAlign.Start)) {
            withStyle(SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Normal)) {
                append("Вечірній Одинадцятий Крок пропонує нам:\n")
                append(" - молитву;\n - медитацію;\n - інвентаризацію.")
            }
        }
        /* Space*/
//        withStyle(SpanStyle(fontSize = 4.sp)) { append(" ") }
        withStyle(ParagraphStyle(lineHeight = 4.sp)) {}

        withStyle(
            ParagraphStyle(
                textAlign = TextAlign.Justify,
//                textIndent = TextIndent(firstLine = 30.sp)
            )
        ) {
            withStyle(SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Normal)) {
                append(
                    "Коли я лягаю спати, то прошу Бога допомогти мені ретельно проаналізувати" +
                            " пройдешній день: \n"
                )
                append(
                    "1.Чи був я ображений на когось? Чи був я егоїстичним, або нечесним, чи зляканим?\n" +
                            "2. Чи мені слід вибачитися перед кимось, або відшкодувати завдані збитки?\n" +
                            "3. Чи не причаїв я в собі щось таке, що повинен був обговорити це з кимось?\n" +
                            "4. Чи був я до всіх привітним, добрими і люблячим? \n" +
                            "5. Що я міг би зробити краще?\n" +
                            "6. Чи думав я про те, що міг би зробити для інших? Чи переважно я думав про себе?\n" +
                            "7. Чи замислювався я над тим, чим заповнюю потік свого життя? "
                )

                /* Space*/
                withStyle(ParagraphStyle(lineHeight = 4.sp)) {}

                append(
                    "Зробивши інвентаризацію я молюся і прошу прощення у Бога за помилки і запитую, " +
                            "що слід зробити для їх виправлення.\n" +
                            "Однак я мушу бути обережним, щоб це поволі не перейшло у тривогу, жаль або" +
                            " хворобливі роздуми, бо це зменшить мою корисність для Бога і інших."
                )
            }

            /* Space*/
            withStyle(ParagraphStyle(lineHeight = 4.sp)) {}

            withStyle(ParagraphStyle(textAlign = TextAlign.Center)) {
                withStyle(SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Normal)) {
                    append("*****")
                }
            }
            /* Space*/
            withStyle(ParagraphStyle(lineHeight = 4.sp)) {}


            withStyle(SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Normal)) {
                append(
                    "Боже, я дякую тобі за те, що прожив ще один тверезий день. Допоможи мені побачити мої" +
                            " досягнення сьогодні і не знецінювати те, що було сьогодні. Я прошу у Тебе вибачення за ті" +
                            " помилки, які я припустився. Я знаю, що через мої помилки, я не міг бути таким ефективним" +
                            " у служінні Тобі та виконанні Твоєї волі яким би я міг бути. Прошу Тебе, прости мене і" +
                            " допоможи завтра виконувати волю Твою краще.\n" +
                            "Боже, я прошу Тебе покажи мені, як виправити ті помилки, яких я сьогодні припустився." +
                            " Веди і спрямовуй мене. Будь ласка, видали мою зарозумілість і мої страхи." +
                            " Господи, покажи мені, як покращити мої взаємини з Тобою, моїми братами та сестрами," +
                            " моїми рідними та близькими, даруй мені смирення та силу виконувати Твою волю"
                )

            }
        }
    }
}


/* ***************** О душ.покое ************* */
/* ***************** peace of mind ************* */
fun peaceOfMindText(
    titleSize: Int,
    textSize: Int
): AnnotatedString {
    return buildAnnotatedString {
        withStyle(ParagraphStyle(textAlign = TextAlign.Center)) {
            withStyle(SpanStyle(fontSize = titleSize.sp, fontWeight = FontWeight.Bold)) {
                append("Молитва про душевний спокій")
            }
        }
        /* Space*/
//        withStyle(SpanStyle(fontSize = 4.sp)) { append(" ") }
//        withStyle(ParagraphStyle(lineHeight = 4.sp)) {}

        withStyle(ParagraphStyle(textAlign = TextAlign.Justify)) {
            withStyle(SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Normal)) {
                append(
                    "Боже, даруй мені розум і душевний спокій приймати те, що я не можу змінити," +
                            " мужність змінити те, що можу, І мудрість відрізнити одне від іншого."
                )
            }

        }

        /* Space*/
//        withStyle(SpanStyle(fontSize = 4.sp)) { append(" ") }
        withStyle(ParagraphStyle(lineHeight = 4.sp)) {}

        withStyle(
            ParagraphStyle(
                textAlign = TextAlign.Justify,
                textIndent = TextIndent(firstLine = 30.sp)
            )
        ) {
            withStyle(SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Normal)) {
                append(
                    "Дай мені зустріти з душевним спокоєм усе, що принесе мені нинішній день." +
                            " Дай мені цілковито здатись на Твою святу волю. Веди мене, Господи, в кожній " +
                            "хвилині цього дня та підтримуй мене в усьому. Усі вісті, які б я дістав сьогодні, " +
                            "навчи мене їх прийняти зі спокійною душею та твердим переконанням, що в усьому" +
                            " проявляється Твоя свята воля, Господи Боже мій. В усіх ділах і словах керуй моїми думками."
                )
            }
        }

        /* Space*/
//        withStyle(SpanStyle(fontSize = 4.sp)) { append(" ") }
        withStyle(ParagraphStyle(lineHeight = 4.sp)) {}

        withStyle(
            ParagraphStyle(
                textAlign = TextAlign.Justify,
                textIndent = TextIndent(firstLine = 30.sp)
            )
        ) {
            withStyle(SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Normal)) {
                append(
                    "В усіх непередбачених випадках не дай мені, Господи, забути, що це Ти їх попустив." +
                            " Навчи мене просто й розсудливо ставитись до всіх людей, нікого не ображаючи," +
                            " а ні засмучуючи. В усіх ділах i словах моїх керуй моїми думками. Господи, дай " +
                            "мені силу сьогодні витримати в доброму. Керуй моєю волею і навчи мене молитись" +
                            " і вірити, сподіватись та витримувати, прощати і любити. Нехай буде воля Твоя, " +
                            "Господи, наді мною Амінь.\n"
                )
            }

            withStyle(SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Bold)) {
                append("Нехай збудеться воля Твоя, а не моя!")
            }
        }
    }
}


/* ***************** Resentment ************* */
fun resentmentText(
    titleSize: Int,
    textSize: Int
): AnnotatedString {
    return buildAnnotatedString {
        withStyle(ParagraphStyle(textAlign = TextAlign.Center)) {
            withStyle(SpanStyle(fontSize = titleSize.sp, fontWeight = FontWeight.Bold)) {
                append("Злоба (resentment)")
            }
        }
        /* Space*/
//        withStyle(SpanStyle(fontSize = 4.sp)) { append(" ") }
//        withStyle(ParagraphStyle(lineHeight = 4.sp)) {}

        withStyle(ParagraphStyle(textAlign = TextAlign.Justify)) {
            withStyle(
                SpanStyle(
                    fontSize = textSize.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Normal,
//                    textDecoration = TextDecoration.Underline
                )
            ) {
                append(
                    "Коли мене катує resentment, коли згадка про минуле робить мене скаженним, а " +
                            "страх збуджує агресивні думки і бажання помсти, я відразу звертаюсь до " +
                            "Бога в "
                )
            }
        }
        withStyle(ParagraphStyle(textAlign = TextAlign.Justify)) {
            withStyle(
                SpanStyle(
                    fontSize = textSize.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Normal,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append("молитві:")
            }
        }
        /* Space*/
//        withStyle(SpanStyle(fontSize = 4.sp)) { append(" ") }
        withStyle(ParagraphStyle(lineHeight = 4.sp)) {}

        withStyle(
            ParagraphStyle(
                textAlign = TextAlign.Justify,
                textIndent = TextIndent(30.sp)
            )
        ) {
            withStyle(SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Normal)) {
                append(
                    "Боже, я безсилий перед цим \"resentment\", він вбиває мене. Благаю, позбав " +
                            "мене від страждань і навчи відноситись до (ім\'я) з тим же розумінням," +
                            " співчуттям і любов\'ю - з якою б я із задоволенням поставився до хворого " +
                            "друга чи дитини. Роблю паузу і чекаю на подарунок від Бога у вигляді " +
                            "візуалізації, яким я можу бути вільним, радісним і щасливим, коли Бог" +
                            " звільнить мене від цього \"resentment\", щоб щиро побажати добра" +
                            " (ім\'я). Тому що (ім\'я) така ж хвора людина як і я, чим я можу йому " +
                            "допомогти?"
                )
            }
        }
    }
}


/* ***************** Fear ************* */
fun prayerOfFearText(
    titleSize: Int,
    textSize: Int
): AnnotatedString {
    return buildAnnotatedString {
        withStyle(ParagraphStyle(textAlign = TextAlign.Center)) {
            withStyle(SpanStyle(fontSize = titleSize.sp, fontWeight = FontWeight.Bold)) {
                append("Страхи")
            }
        }
        /* Space*/
//        withStyle(SpanStyle(fontSize = 4.sp)) { append(" ") }
//        withStyle(ParagraphStyle(lineHeight = 4.sp)) {}

        withStyle(
            ParagraphStyle(
                textAlign = TextAlign.Justify,
                textIndent = TextIndent(30.sp)
            )
        ) {
            withStyle(SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Normal)) {
                append(
                    "Господи, допоможи мені побачити, що цей страх (назва страху) через мою надію " +
                            "на самого себе,через самовпевненність і надії на власні сили. Коли я на " +
                            "100% сподіваюся на Тебе- цього страху в мене не має."
                )
            }
        }

        /* Space*/
//        withStyle(SpanStyle(fontSize = 4.sp)) { append(" ") }
        withStyle(ParagraphStyle(lineHeight = 4.sp)) {}

        withStyle(
            ParagraphStyle(
                textAlign = TextAlign.Justify,
                textIndent = TextIndent(30.sp)
            )
        ) {
            withStyle(SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Normal)) {
                append("Господи, допоможи мені побачити, що цей страх (назва страху) через мою " +
                        "надію на самого себе,через самовпевненність і надії на власні сили. Коли " +
                        "я на 100% сподіваюся на Тебе- цього страху в мене не має.")
            }
        }

        /* Space*/
//        withStyle(SpanStyle(fontSize = 4.sp)) { append(" ") }
        withStyle(ParagraphStyle(lineHeight = 4.sp)) {}

        withStyle(
            ParagraphStyle(
                textAlign = TextAlign.Justify,
                textIndent = TextIndent(30.sp)
            )
        ) {
            withStyle(SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Normal)) {
                append("Боже, позбав мене від страху (назва страху) і зверни мою увагу на те, " +
                        "якою мені, згідно з Твоєю волею, належить бути (радісним, вільним та щасливим).")
            }
        }
    }
}


/* ***************** 10 Step ************* */
fun prayerTenText(
    titleSize: Int,
    textSize: Int
): AnnotatedString {
    return buildAnnotatedString {
        withStyle(ParagraphStyle(textAlign = TextAlign.Center)) {
            withStyle(SpanStyle(fontSize = titleSize.sp, fontWeight = FontWeight.Bold)) {
                append("10 Крок")
            }
        }
        /* Space*/
//        withStyle(SpanStyle(fontSize = 4.sp)) { append(" ") }
//        withStyle(ParagraphStyle(lineHeight = 4.sp)) {}

        withStyle(
            ParagraphStyle(
                textAlign = TextAlign.Justify,
                textIndent = TextIndent(30.sp)
            )
        ) {
            withStyle(SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Normal)) {
                append("Десятий Крок пропонує нам постійно робити особисту інвентаризацію, щоб " +
                        "зразу виявляти і виправляти свої помилки.")
            }
        }
        /* Space*/
//        withStyle(SpanStyle(fontSize = 4.sp)) { append(" ") }
        withStyle(ParagraphStyle(lineHeight = 4.sp)) {}

        withStyle(ParagraphStyle(textAlign = TextAlign.Justify)) {
            withStyle(
                SpanStyle(
                    fontSize = textSize.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Normal,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append("Алгоритм десятого шага:")
            }
        }

        /* Space*/
//        withStyle(SpanStyle(fontSize = 4.sp)) { append(" ") }
        withStyle(ParagraphStyle(lineHeight = 4.sp)) {}

        withStyle(
            ParagraphStyle(
                textAlign = TextAlign.Justify,
            )
        ) {
            withStyle(SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Normal)) {
                append("1. Продовжуємо спостерігати за егоїзмом, нечесністю, образами і страхом.\n" +
                        "Коли вони проявляються, то:\n" +
                        "2. Одразу просимо Бога усунути їх;\n" +
                        "3. Мнегайно обговорюємо їх з кимось;\n" +
                        "4. Швидко відшкодовуємо збитки, якщо ми когось скривдили;\n" +
                        "5. Тоді ми рішуче звертаємо думки до когось, кому ми можемо допомогти.")
            }

            /* Space*/
//            withStyle(SpanStyle(fontSize = 4.sp)) { append(" ") }
            withStyle(ParagraphStyle(lineHeight = 4.sp)) {}

            withStyle(SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Bold)){
                append("Правило нашого життя — це любов і терпимість до інших.")
            }
        }
    }
}



/* ***************** 1 - 2 Step ************* */
fun stepOneAndTwoText(
    titleSize: Int,
    textSize: Int
): AnnotatedString {
    return buildAnnotatedString {
        withStyle(ParagraphStyle(textAlign = TextAlign.Center)) {
            withStyle(SpanStyle(fontSize = titleSize.sp, fontWeight = FontWeight.Bold)) {
                append("1-2 Кроки")
            }
        }
        /* Space*/
//        withStyle(SpanStyle(fontSize = 4.sp)) { append(" ") }
//        withStyle(ParagraphStyle(lineHeight = 4.sp)) {}

        withStyle(ParagraphStyle(textAlign = TextAlign.Center)) {
            withStyle(
                SpanStyle(
                    fontSize = textSize.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Normal,
//                    textDecoration = TextDecoration.Underline
                )
            ) {
                append("Навіщо мені потрібен Бог.")
            }
        }

        withStyle(
            ParagraphStyle(
                textAlign = TextAlign.Justify,
                textIndent = TextIndent(30.sp)
            )
        ) {
            withStyle(SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Normal)) {
                append(
                    "Боже, Ти мені дуже потрібен, тому що я хворий на невиліковну недугу – алкоголізм." +
                            " Я робив багато самостійних спроб контролювати кількість випитого алкоголю, але," +
                            " майже завжди зазнавав поразки і напивався.\n Багато людей намагалися допомогти" +
                            " мені позбавитись алкогольного безумства. Умовляли, лікували, щоб я жив як" +
                            " нормальна людина, але, це також не дало бажаних результатів - я постійно" +
                            " зривався.\n Нарешті я визнав своє безсилля і переконався, що немає ніяких" +
                            " людських сил, які б могли мені допомогти. Повірив, що тільки Ти, Боже," +
                            " для мене надія, опора і спасіння.\n Благаю за милосердя і зцілення," +
                            " бо Ти сильніше і мудріше мого занепалого єства. Молю, зціли пошкодження" +
                            " моєї хворої плоті і волі, бо Ти мій Творець і всякого блага Подавець," +
                            " в Тобі вся надія моя - нині, і повсякчас, і на віки віків. Амінь."
                )
            }
        }
    }
}


/* ***************** 6 Step ************* */
fun stepSixText(
    titleSize: Int,
    textSize: Int
): AnnotatedString {
    return buildAnnotatedString {
        withStyle(ParagraphStyle(textAlign = TextAlign.Center)) {
            withStyle(SpanStyle(fontSize = titleSize.sp, fontWeight = FontWeight.Bold)) {
                append("6-7 Кроки")
            }
        }
        /* Space*/
//        withStyle(SpanStyle(fontSize = 4.sp)) { append(" ") }
//        withStyle(ParagraphStyle(lineHeight = 4.sp)) {}

        withStyle(
            ParagraphStyle(
                textAlign = TextAlign.Justify,
                textIndent = TextIndent(30.sp)
            )
        ) {
            withStyle(SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Normal)) {
                append(" Я з подякою молюся Господу за подаровану мені можливість усвідомлювати, що" +
                        " я дитя Бога, свята душа в людській оболонці, і що моє основне завдання в" +
                        " цьому житті- пізнавати, приймати, любити і виховувати самого СЕБЕ.\n" +
                        " Пізнаючи і люблячи себе, як Боже творіння- я пізнаю і люблю Бога.\n Приймаючи" +
                        " себе таким, який я є - я приймаю Божу волю.\n Виховуючи себе- я дію" +
                        " по волі Божій і молюся про бажання щоб позбавитись егоїзму та смиренно" +
                        " славлю Бога. Амінь.\n Не робіть «проекту» з роботи по Кроках, живіть" +
                        " просто,  сьогодні будьте тою людиною, якою ви прагнете бути. Намагайтесь" +
                        " допомагати іншим людям, і слідкуйте за тим, щоб не завдати нікому болю. А" +
                        " коли збираєтесь лягати спати, то візьміть книгу АА і прочитайте дванадцять" +
                        " Кроків, і ви будете приємно здивовані тим фактом, що весь день жили за " +
                        "програмою.\nБілл В.")
            }
        }
    }
}

/* ***************** 7 Step ************* */
fun stepSevenText(
    titleSize: Int,
    textSize: Int
): AnnotatedString {
    return buildAnnotatedString {
        withStyle(ParagraphStyle(textAlign = TextAlign.Center)) {
            withStyle(SpanStyle(fontSize = titleSize.sp, fontWeight = FontWeight.Bold)) {
                append("Молитва 7-го кроку")
            }
        }
        /* Space*/
//        withStyle(SpanStyle(fontSize = 4.sp)) { append(" ") }
//        withStyle(ParagraphStyle(lineHeight = 4.sp)) {}

        withStyle(
            ParagraphStyle(
                textAlign = TextAlign.Justify,
                textIndent = TextIndent(30.sp)
            )
        ) {
            withStyle(SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Normal)) {
                append("Боже, Отче мій небесний.Навчи мене жити в рівновазі між моїми вадами характеру " +
                        "і духовними принципами програми 12 Кроків, щоб служити Тобі, Господи, " +
                        "приносити користь людям і дбати про себе і близьких. Господи, даруй мені " +
                        "смирення і здоровий розум, щоб виконувати волю Твою, а не свою. Амінь.")
            }
        }
    }
}


/* ***************** prayer for powerlessness ************* */
fun prayerPowerlessnessText(
    titleSize: Int,
    textSize: Int
): AnnotatedString {
    return buildAnnotatedString {
        withStyle(ParagraphStyle(textAlign = TextAlign.Center)) {
            withStyle(SpanStyle(fontSize = titleSize.sp, fontWeight = FontWeight.Bold)) {
                append("Молитва про безсилля")
            }
        }
        /* Space*/
//        withStyle(SpanStyle(fontSize = 4.sp)) { append(" ") }
//        withStyle(ParagraphStyle(lineHeight = 4.sp)) {}

        withStyle(
            ParagraphStyle(
                textAlign = TextAlign.Justify,
                textIndent = TextIndent(30.sp),
                hyphens = Hyphens.Auto // ???
            )
        ) {
            withStyle(SpanStyle(fontSize = textSize.sp, fontWeight = FontWeight.Normal)) {
                append("Господи, я молюся про те, щоб звільнитися від своєї звички бути стурбованою" +
                        " станом справ. Адже так часто я переконувався, що від мене нічого не залежить." +
                        " Нехай же я зможу зрозуміти і відчути своє безсилля! Лише тоді, нарешті, я" +
                        " перестану даремно метушитися. Спокійно і розумно буду приймати те, що мені" +
                        " посилається Богом! Господи! Ти мій Батько, Ти мій Друг, Ти мій Заступник," +
                        " Ти мій Втішник, Ти мій Керівник. Ти — мир у моєму серці, Ти — радість у" +
                        " моїх очах, Ти — любов у моїй  душі. Дякую Тобі за допомогу і керівництво," +
                        " дякую Тобі за мудрість і терпіння, з якими Ти виховуєш мене і дбаєш про мене." +
                        " Дякую Тобі за те, що навіть коли я злюся на Тебе і чую тільки себе, Ти не" +
                        " залишаєш мене. Я знаю, що все, що зараз здається мені крахом і руйнуванням," +
                        " згодом виявиться найкращим для мене. Я прошу Тебе, даруй мені довіру до" +
                        " Тебе і Твого керівництва, прийняття і усвідомлення реальності, позбавлення" +
                        " від ілюзій і страхів,  покірність з нинішнім станом речей і безстрашність" +
                        " перед невідомістю. Допоможи мені, відкинувши всі свої сумніви і миттєві" +
                        " бажання маленької дитини, з хвилюванням чекати Твоїх чудес, жити тут і" +
                        " зараз з вдячністю за життя і Твої дари, ставитися спокійно і адекватно до" +
                        " своїх емоцій і переживань і відпускати події і людей. Допоможи мені" +
                        " перестати контролювати своє життя і чинити опір змінам, вірячи, що всі" +
                        " вони за Твоєю Волею і на краще. Допоможи мені в своїх змінах не" +
                        " концентруватися на собі, а нести в цей світ радість, турботу і служіння." +
                        " Навчи мене діяти з кращих побуджень і звільни від страху не отримати" +
                        " бажане, бути ображеним і відкинутим Допоможи мені розслабитися і відпустити" +
                        " себе за Твоєю Волею. Амінь.")
            }
        }

    }
}



