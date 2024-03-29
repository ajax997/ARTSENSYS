package test;

import artsensys.dbcontroller.AddingExtending;
import artsensys.dbcontroller.ObjectController;
import artsensys.dbcontroller.neo4jcontroller.Extending;
import artsensys.dbcontroller.neo4jcontroller.Neo4JInteraction;
import artsensys.dbcontroller.neo4jcontroller.Neo4jObjectHelper;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.types.Node;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*;
import java.util.logging.LogManager;

/**
 * Created by nguyennghi on 12/10/17 1:05 PM.
 */
public class Main {
    //static Neo4JInteraction neo4JInteraction;
    public static void main(String... args) throws Exception {
        LogManager.getLogManager().reset();
//        String str = "In 1938, Ziff-Davis, a Chicago-based publisher looking to expand into the pulp magazine market, acquired Amazing Stories.[2] The number of science fiction magazines grew quickly, and several new titles appeared over the next few years—among them Fantastic Adventures, which was launched by Ziff-Davis in 1939 as a companion to Amazing.[3] Under the editorship of Raymond Palmer, the magazines were reasonably successful but published poor-quality work; when Howard Browne took over as editor of Amazing in January 1950, he decided to try to move the magazine upmarket.[4][5] Ziff-Davis agreed to back the new magazine, and Browne put together a sample copy, but, when the Korean War broke out, Ziff-Davis cut their budgets and the project was abandoned.[6] Browne did not give up, and in 1952 received the go-ahead to try a new magazine instead, focused on high-quality fantasy,[7] a genre which had recently become more popular.[8] The first issue of Fantastic, dated Summer 1952, appeared on March 21 of that year.[7]\n" +
//                "Issues of Fantastic through 1960, identifying volume and issue numbers, and\n" +
//                "indicating editors: in sequence, Howard Browne, Paul Fairman, and Cele\n" +
//                "Goldsmith. Underlining indicates that an issue was titled as a quarterly (i.e.,\n" +
//                "\"Fall 1952\") rather than as a monthly.\n" +
//                "Sales were very good, and Ziff-Davis was sufficiently impressed after only two issues to move the magazine from a quarterly to a bimonthly schedule, and to switch Amazing from pulp format to digest-size to match Fantastic. Shortly afterwards the decision was taken to eliminate Fantastic Adventures: the March 1953 issue was the last, and the May–June 1953 issue of Fantastic added a mention of Fantastic Adventures to the masthead, though this ceased with the following issue.[7] Payment started at two cents per word for all rights, but could go up to ten cents at the editor's discretion; this put Fantastic in the second echelon of magazines, behind titles such as Astounding and Galaxy.[9][10] The experiment with quality fiction did not last. Circulation dropped, which led to budget cuts, and in turn the quality of the fiction fell. Browne had wanted to separate Fantastic from Amazing's pulp roots, but now found he had to print more science fiction (sf) and less fantasy in order to attract Amazing's readers to its sister magazine.[7] Fantastic's poor results were probably a consequence of an overloaded sf-magazine market: far more magazines appeared in the early 1950s than the market was able to support. Ziff-Davis sales staff were able to help sell Fantastic and Amazing along with the technical magazines that it published, and the availability of a national sales network, even though it was not focused solely on Fantastic, undoubtedly helped the magazine to survive.[11]\n" +
//                "\n" +
//                "In May 1956, Browne left Ziff-Davis to become a screenwriter. Paul W. Fairman took over as editor of both Fantastic and Amazing. In 1957, Bernard Davis left Ziff-Davis; it had been Davis who had suggested the acquisition of Amazing in 1939, and he had stayed involved with the sf magazines throughout the time he spent there. With his departure Amazing and Fantastic stagnated; they were still issued monthly, but drew no attention from the management of Ziff-Davis.[12]\n" +
//                "\n" +
//                "Mid-1950s to late 1960s\n" +
//                "In November 1955, Ziff-Davis hired an assistant, Cele Goldsmith, who began by helping with two new magazines under development, Dream World and Pen Pals. She also read the slush piles for all the magazines, and was quickly given more responsibility. In 1957, she was made managing editor of both Amazing and Fantastic, doing administrative chores and reading unsolicited manuscripts. At the end of 1958, she became editor, replacing Fairman, who had left to edit Ellery Queen's Mystery Magazine.[1][12] Goldsmith—who became Cele Lalli when she married in 1964—stayed as editor for six and a half years.[1]\n" +
//                "\n" +
//                "Issues of Fantastic from 1961 to 1970, identifying volume and issue numbers, and\n" +
//                "indicating editors: in sequence, Cele Goldsmith (Lalli), Joseph Ross, Harry Harrison,\n" +
//                "Barry Malzberg, and Ted White\n" +
//                "Circulation dropped for both Amazing and Fantastic: in 1964, Fantastic had a paid circulation of only 27,000.[13] In 1965, Sol Cohen, who at that time was Galaxy's publisher, set up his own publishing company, Ultimate Publishing, and bought both Amazing and Fantastic from Ziff-Davis.[1][notes 1] Cohen had decided to make the magazines as profitable as possible by filling them only with reprints. This was possible because Ziff-Davis had acquired second serial rights[notes 2] for all stories they had published, and since Cohen had bought the backfile of stories he was able to reprint them using these rights.[1][16] Using reprints in this way saved Cohen about\n" +
//                "\n" +
//                "REDIRECT Template:US$ a year between the two magazines.[16] Lalli decided that she did not want to work for Cohen, and stayed with Ziff-Davis. Her last issue was June 1965. Cohen replaced Lalli with Joseph Wrzos, who used the name \"Joseph Ross\" on the magazines.[1][13] Cohen had met Wrzos at the Galaxy offices not long before; Wrzos was teaching English full-time, but had worked for Gnome Press as an assistant editor in 1953–1954.[13]\n" +
//                "Cohen also launched a series of reprint magazines, drawing from the backfile of both Amazing and Fantastic, again using the second serial rights he had acquired from Ziff-Davis. The first reprint magazine was Great Science Fiction; the first issue, titled Great Science Fiction from Amazing, appeared in August 1965. By early 1967 this had been joined by The Most Thrilling Science Fiction Ever Told and Science Fiction Classics. These increased the workload on Wrzos, though Cohen made the selection of stories, and Wrzos found himself able to work on Fantastic and Amazing only part-time. Cohen hired Herb Lehrman to help with the other magazines.[13]\n" +
//                "\n" +
//                "Although Cohen felt that his deal with Ziff-Davis gave him the reprint rights he needed, the newly formed Science Fiction Writers of America (SFWA) received complaints about Cohen's refusal to pay anything for the reprints. He was also reportedly not responding to requests for reassignment of copyright. SFWA organized a boycott of Cohen's magazines; after a year Cohen agreed to pay a flat fee for the reprints, and in August 1967 he agreed to a graduated scale of payments, and the boycott was withdrawn.[13]\n" +
//                "\n" +
//                "\n" +
//                "Circulation and sellthrough (percentage of print run sold) for Fantastic\n" +
//                "Harry Harrison had been involved in the negotiations between SFWA and Cohen, and when the agreement was reached in 1967 Cohen asked Harrison if he would take over as editor of both magazines. Harrison was available because SF Impulse, which he had been editing, had ceased publication in early 1967. Cohen agreed to phase out the reprints by the end of the year, and Harrison took the job. Cohen added Harrison's name to the masthead of two issues of Great Science Fiction, although Harrison had had nothing to do with that magazine, but the reprints in Fantastic and Amazing continued and Harrison decided to quit in February 1968. He recommended Barry Malzberg as his replacement. Cohen had worked with Malzberg at the Scott Meredith Literary Agency, and felt Malzberg would be more cooperative than Harrison. Malzberg, however, turned out to be just as unwilling as Harrison to work with Cohen if the reprints continued, and soon regretted taking the job. In October 1968 Cohen refused to pay for a cover that Malzberg had commissioned; Malzberg insisted, threatening to resign if Cohen did not agree. Cohen contacted Robert Silverberg, then the president of SFWA, and told him (falsely) that Malzberg had actually resigned. Silverberg recommended Ted White as a replacement. Cohen secured White's agreement and then fired Malzberg; White took over in October 1968, but because there was a backlog of stories Malzberg had acquired, the first issue on which he was credited as editor was the June 1969 issue.[13][16]\n" +
//                "\n" +
//                "1970s to present\n" +
//                "Like his immediate predecessors, White took the job on condition that the reprints would be phased out. It was some time before this was achieved: there was at least one reprinted story in every issue until the end of 1971. The February 1972 issue contained some artwork reprinted from 1939, and after that the reprints ceased.[16][17]\n" +
//                "\n" +
//                "Jan\tFeb\tMar\tApr\tMay\tJun\tJul\tAug\tSep\tOct\tNov\tDec\n" +
//                "Issues of Fantastic from 1971 to 1980, identifying volume and issue numbers, and\n" +
//                "indicating editors: Ted White through most of the decade, and then Elinor Mavor.\n" +
//                "Note that the apparent error in volume numbering at the end of 1977 is in fact correct.\n" +
//                "Fantastic's circulation was about 37,000 when White took over; only about 4 percent of this was subscription sales. Cohen's wife filled the subscriptions from their garage, and according to White, Cohen regarded this as a burden, and never tried to increase the subscription base.[16] Despite White's efforts, Fantastic's circulation fell, from almost 37,000 when he took over as editor to less than 24,000 in the summer of 1975. Cohen was rumored to be interested in selling both Fantastic and Amazing; among other possibilities, both Roger Elwood, at that time an active science fiction anthology editor, and Edward Ferman, the editor of The Magazine of Fantasy & Science Fiction, approached Cohen with a view to acquiring the titles. Nothing came of it, however, and White was not aware of the possible sales. He was working at a low salary, with unpaid help from friends to read unsolicited submissions—at one point he introduced a 25-cent reading fee for manuscripts from unpublished writers; the fee would be refunded if White bought the story. White sometimes found himself at odds with Cohen's business partner, Arthur Bernhard, due to their different political views.[18] White's unhappiness with his working conditions culminated in his resignation after Cohen refused his proposal to publish Fantastic as a slick magazine, with larger pages and higher quality paper.[18] White commented in an article in Science Fiction Review that he had brought to the magazines \"a lot of energy and enthusiasm and a great many ideas for their improvement ...Well, I have put into effect nearly every idea which I was allowed to follow through on ... and have spent most of my energy and enthusiasm.\"[19] Cohen was able to persuade him to stay for another year; in the event White stayed for another three.[18]\n" +
//                "\n" +
//                "White was unable to completely halt the slide in circulation, though it rose a little in 1977. That year Cohen lost $15,000 dollars on the magazines, and decided to sell.[18] He spent some time looking for a new publisher—editor Roy Torgeson was one of those interested—but on September 15, 1978, he sold his half of the business to Arthur Bernhard, his partner.[20] White renewed his suggestions for improving the format of the magazine: he wanted to make Fantastic the same size as Time, and believed he could avoid the mistakes that had been made by other sf magazines that had tried that approach. White also proposed an increase in the budget and asked for a raise. Bernhard not only turned down White's ideas, but also stopped paying him: White responded by resigning. His last official day as editor was November 9; the last issue of Fantastic under his control was the January 1979 issue. He returned all submissions to their authors, saying that he had been told to do so by Bernhard; Bernhard denied this.[20]\n" +
//                "\n" +
//                "Bernhard brought in Elinor Mavor to edit both Amazing and Fantastic. Mavor had previously edited Bill of Fare, a restaurant trade journal, and was a long-time science fiction reader, but she had little knowledge of the history of the magazines. She was unaware, for example, that she was not the first woman to edit them, and so adopted a male pseudonym—\"Omar Gohagen\"—for a while.[20] She suggested a campaign to increase circulation, and went so far as to gather information about costs while on a trip to New York in 1979. Bernhard decided instead to merge the two magazines. Circulation was continuing to drop; the figures for the last two years are not available, but sf historian Mike Ashley estimates that Fantastic's paid circulation may have been as low as 13,000.[notes 3] Bernhard felt that since Fantastic had never been profitable, whereas Amazing had made money, it was best to keep Amazing.[20] Until the March 1985 issue, Amazing included a mention of Fantastic on the spine and on the contents page.[17] In 1999, the fiction magazine formerly known as Pirate Writings revived the Fantastic title and Cele Goldsmith-era logotype for several issues, ultimately unsuccessfully, though this was not intended as a continuation of the original magazine.[21]\n" +
//                "\n" +
//                "In August 2014, Warren Lapine, former editor of Absolute Magnitude, Realms of Fantasy, and Weird Tales, revived the Fantastic logotype of Fantastic Stories of the Imagination as a free webzine.[22]\n" +
//                "\n" +
//                "Contents and reception\n" +
//                "Browne and Fairman\n" +
//                "\n" +
//                "Cover of first issue, by Barye Phillips and Leo Summers\n" +
//                "The first issue of Fantastic was impressive, with a cover that sf historian Mike Ashley has described as \"one of the most captivating of all first issues\"; the painting, by Barye Phillips and Leo Summers, illustrated Kris Neville's \"The Opal Necklace\". The fiction included some stories by well known names; in particular, Raymond Chandler's \"Professor Bingo's Snuff\" would have caught readers' eyes—the story had appeared the year before in Park East magazine, but would have been new to most readers. It was a short mystery in which the fantasy element was invisibility, achieved by magical snuff. Isaac Asimov and Ray Bradbury also contributed stories, and the issue led with \"Six and Ten Are Johnny\", by Walter M. Miller. The rear cover reprinted Pierre Roy's painting \"Danger on the Stairs\", which depicted a snake on a staircase; it was an odd choice, but subsequent back covers were more natural fits for a fantasy magazine. The quality of the fiction continued to be high for the first year; sf historian Mike Ashley comments that almost every story in the first seven issues was of high quality,[7] and historian David Kyle regards it as an \"outstandingly successful experiment\".[23] Science fiction bibliographer Donald Tuck dissents, however, regarding the first few years as containing \"little of note\",[24] and James Blish wrote a contemporary review of the second issue which found it lacking: Blish dismissed three of the seven stories in the Fall 1952 issue as being essentially crime stories written for the sf market, and commented that of the remaining four, only two were \"reasonably competent and craftsmanlike\".[25]\n" +
//                "\n" +
//                "Other well-known writers appeared in the early issues, including Shirley Jackson, B. Traven, Truman Capote and Evelyn Waugh.[8] Mickey Spillane had written a story called \"The Woman With Green Skin\", but had been unable to sell it; Browne offered to buy it on condition that he had permission to rewrite it as he wished. This was agreed and Browne scrapped Spillane's text completely, writing a new story called \"The Veiled Woman\" and publishing it as by Spillane in the November–December 1952 issue. The issue sold so well it was reprinted, with over 300,000 copies sold.[7]\n" +
//                "\n" +
//                "The emphasis was on fantasy, and much of it was \"slick\" fantasy—the sort of genre fiction that the upmarket slick magazines, such as The Saturday Evening Post, were willing to buy.[8] Some science fiction appeared as well in the first couple of years, including Isaac Asimov's \"Sally\", which portrays a world in which cars have been given robotic brains and are intelligent.[7] In 1955 it was decided to move the focus from fantasy to sf: in Browne's words, \"Stories of straight fantasy were largely eliminated and straight science-fiction substituted, cover subject matter became of a scientific nature, the words \"science fiction\" appeared under the title, interior artwork was tightened up to replace the loose, 'arty' kind of drawing we had been using.\" Sales rose 17% within two issues.[7][26] Browne was uninterested in science fiction, however, and the quality of the fiction soon dropped, with a small stable of writers producing much of Fantastic's fiction under house names over the next couple of years.[7] By the start of 1956 the fiction in Fantastic was, in the opinion of sf historian Mike Ashley, \"[in] a trough of hack predictability\",[27] but there was some inventiveness evident from newer writers such as Robert Silverberg, Harlan Ellison and Randall Garrett.[8]\n" +
//                "\n" +
//                "\n" +
//                "The second wish fulfilment cover, for October 1956, by Ed Valigursky\n" +
//                "Although Browne had been unable to make Fantastic successful by specializing in fantasy, he was still interested in the fantasy genre, and experimented in the December 1955 issue with the theme of wish fulfilment. He dropped the words \"Science Fiction\" from the cover, and published five stories, all of which dealt with male fantasies in one form or another. The cover showed a man walking through a wall to find a woman undressing; the art was by Ed Valigursky and illustrated Paul Fairman's \"All Walls Were Mist\". Reader reaction, according to Browne, was almost entirely favorable, and he continued to publish occasional stories on the wish-fulfilment theme. The experiment was repeated with the October 1956 issue, which again ran without \"Science Fiction\" on the cover, and contained stories on the theme of \"Incredible Powers\". Once again the cover illustrated a male fantasy: this time it showed a man materializing in a bath house where women were showering. Browne had left Ziff-Davis by the time this issue appeared, but Browne's plans for a magazine around these themes were well advanced, and Fairman, who by this time was editing both Fantastic and Amazing, was given Dream World to edit as well. It ran for three quarterly issues, starting in February 1957, but proved too narrow a market to succeed.[28]\n" +
//                "\n" +
//                "Fairman devoted the July 1958 issue of Fantastic to the Shaver Mystery—a lurid set of beliefs propounded by Richard Shaver in the late 1940s that told of \"detrimental robots\", or \"deros\", who were behind many of the disasters that befell humanity. Most of these stories had run in Amazing, though the editor at that time, Ray Palmer, had been forced to drop Shaver by Ziff-Davis when the stories began to attract ridicule in the press. Fantastic's readers were no kinder, complaining vigorously.[29][30]\n" +
//                "\n" +
//                "Goldsmith\n" +
//                "When Goldsmith took over as editor, there was some concern at Ziff-Davis that she might not be able to handle the job. A consultant, Norman Lobsenz, was brought in to help her; Lobsenz's title was \"editorial director\", but in fact Goldsmith made the story selections. Lobsenz provided blurbs and editorials, read the stories Goldsmith bought, and met with Goldsmith every week or so. Goldsmith was not a long-time sf reader, and knew little about the field; she simply looked for good quality fiction and bought what she liked. In Mike Ashley's words, \"the result, between 1961 and 1964, was the two most exciting and original magazines in the field\". New writers whose first story appeared in Fantastic during this period included Phyllis Gotlieb, Larry Eisenberg, Ursula K. Le Guin, Thomas M. Disch, and Piers Anthony.[1] The November 1959 issue was dedicated to Fritz Leiber; it included \"Lean Times in Lankhmar\", one of Leiber's Fafhrd and the Gray Mouser stories. Goldsmith published another half-dozen stories in the series over the next six years, along with other similar (and sometimes imitative) fiction such as early work by Michael Moorcock, and John Jakes' early stories of Brak the Barbarian. This helped to invigorate the nascent sword and sorcery subgenre.[1][8][17] Goldsmith obtained an early story by Cordwainer Smith, \"The Fife of Bodidharma\", which ran in the June 1959 issue, but shortly thereafter Pohl at Galaxy reached an agreement to get first refusal on all Smith's work.[1]\n" +
//                "\n" +
//                "During the early 1960s Goldsmith managed to make Fantastic and Amazing, in the words of Mike Ashley, \"the best-looking and brightest\" magazines around. This applied both to the covers, where Goldsmith used artists such as Alex Schomburg and Leo Summers, and the content.[1] Ashley also describes Fantastic as the \"premier fantasy magazine\" during Goldsmith's tenure—at that time the only other magazine focused specifically on fantasy fiction was the British Science Fantasy.[8]\n" +
//                "\n" +
//                "Goldsmith's tastes were too diverse for Fantastic to be limited to genre fantasy, however, and her willingness to buy fiction she liked, regardless of genre expectations, allowed many new writers to flourish on the pages of both Amazing and Fantastic. Writers such as Ursula K. Le Guin, Roger Zelazny and Thomas M. Disch sold regularly to her at the start of their careers[8] Le Guin later commented that Goldsmith was \"as enterprising and perceptive an editor as the science fiction magazines ever had\".[31] Not all Goldsmith's choices were universally popular with the magazine's subscribers: she regularly published fiction by David R. Bunch, for example, to mixed reviews from the readership.[8]\n" +
//                "\n" +
//                "Reprint era\n" +
//                "Wrzos persuaded Cohen that both Amazing and Fantastic should carry a new story in every issue, rather than running nothing but reprints; Goldsmith had left a backlog of unpublished stories, and Wrzos was able to stretch these out for some time. One such story was Fritz Leiber's \"Stardock\", another Fafhrd and Gray Mouser story, which appeared in the September 1965 issue; it was subsequently nominated for a Hugo Award. The reprints were well received by the fans, because Wrzos was able to find good quality stories that were unavailable except in the original magazines, meaning that to many of Fantastic's readers they were fresh material. Wrzos also reprinted \"The People of the Black Circle\", a Robert E. Howard story from Weird Tales, in 1967, when Howard's Conan stories were becoming popular.[13]\n" +
//                "\n" +
//                "In addition to the backlog of new stories from the Ziff-Davis era, Wrzos was able to acquire some new material. He was especially glad to acquire \"For a Breath I Tarry\", by Roger Zelazny; however, he had to wait for Cohen's approval for his acquisitions. Cohen, perhaps uncertain because of the story's originality, delayed until it appeared in the British magazine New Worlds before agreeing to publish it. Wrzos commented years later that he would \"never forgive him [Cohen] his timidity at that time\".[13] Wrzos bought Doris Piserchia's first story, \"Rocket to Gehenna\", and was the first editor to acquire a story by Dean Koontz. He had to work with Koontz to improve it, and the delay this caused, in addition to the slow publishing schedule for new material, meant that Koontz appeared in print with \"Soft Come the Dragons\", in the August 1967 Fantasy & Science Fiction, before \"A Darkness in My Soul\" appeared in the January 1968 Fantastic.[13]\n" +
//                "\n" +
//                "After Wrzos's departure, Harrison and Malzberg had little opportunity to reshape the magazine as between them they only took responsibility for a handful of issues before Ted White took over. However, Harrison did print James Tiptree's first sale, \"Fault\", in the August 1968 issue; again the slow schedule meant that this was not Tiptree's first appearance in print. Harrison added a science column by Leon Stover, but was unable to change Cohen's position on the reprints, and so could not print much new fiction. When Malzberg took over from Harrison he published John Sladek, Thomas M. Disch, and James Sallis, all of whom were associated with New Wave science fiction, but his tenure was too short for him to have a significant impact on the magazine.[13]\n" +
//                "\n" +
//                "White and Mavor\n" +
//                "\n" +
//                "The main variations in title fonts. Issues shown are Summer 1952, September–October 1953, January 1961, January 1964, June 1971, October 1978, and April 1979: each is the first issue which used each style shown.\n" +
//                "White was only able to offer his writers one cent per word, which was substantially lower than the leading magazines in the field—Analog Science Fiction and Fact paid five cents, and Galaxy and Fantasy & Science Fiction paid three. Most stories would only be submitted to White once the higher-paying markets had rejected them, but among the rejects White was sometimes able to find experimental material that he liked. For example, Piers Anthony had been unable to sell an early fantasy novel, Hasan; White saw a review of the manuscript and promptly acquired it for Fantastic, where it was serialized starting in the December 1969 issue.[16] White also took care to establish relationships with newer writers. White bought Gordon Eklund's first story, \"Dear Aunt Annie\", it appeared in the April 1970 issue and was nominated for a Nebula award. Eklund was unwilling to become a full-time writer, despite this success, because of the financial risks, so White agreed to buy anything Eklund wrote, on condition that Eklund himself believed it was a good story. The result was that much of Eklund's fiction appeared in Amazing and Fantastic over the next few years.[32] In addition to experimental work, White was able to obtain material by some of the leading sf writers of the day, including Brian Aldiss and John Brunner.[16] White also acquired some early work by writers who became better known in other fields: Roger Ebert sold two stories in the early 1970s to Fantastic; the first, \"After the Last Mass\", appeared in the February 1972 issue; and in 1975 White bought Ian McEwan's second story, \"Solid Geometry\". It was included in First Love, Last Rites, McEwan's first short story collection, which won the Somerset Maugham Award in 1976.[16]\n" +
//                "\n" +
//                "White had been an active science fiction fan before he became professionally involved in the field, and although he estimated that only 1 in 30 readers were active sf fans, he tried to use this fan base to help by urging the readership to give him feedback and to help with distribution by checking local newsstands for the magazines. White wanted to introduce established artists from outside the sf field, such as Jeff Jones, Vaughn Bodé, and Steve Hickman; however, the company was saddled with cheap artwork acquired from European magazines to be used for the cover and he was instructed to make use of them.[16] He commissioned a comic strip from Vaughn Bodé, but was outbid by Judy-Lynn Benjamin at Galaxy; he subsequently told his readers that he'd signed up Bodé again for interior artwork, but this never materialized.[33][34][35] Instead a four-page comic strip by Jay Kinney appeared in December 1970; a second strip, by Art Spiegelman, was planned, but never published.[16] Eventually White was allowed to commission original cover art; he published early work by Mike Hinge, and Mike Kaluta made his first professional sale to Fantastic. He tried to hire Hinge as art director, but this fell through and White filled the role himself, sometimes using the pseudonym \"J. Edwards\".[16]\n" +
//                "\n" +
//                "Because of poor distribution, Fantastic was never able to benefit from the increasing popularity of the fantasy genre, though White was able to publish several stories by well-known writers in the field, including a sword and sorcery novella by Dean R. Koontz, which appeared in the October 1970 issue, and an Elric story by Michael Moorcock in February 1972.[8][16] A revival of Robert E. Howard's character Conan, in stories by L. Sprague de Camp and Lin Carter, was successful at increasing sales; the first of these stories appeared in August 1972, and White reported that sales of that issue were higher than for any other issue of Amazing or Fantastic that year. Each Conan story, according to White, increased sales of that issue by 10,000 copies. White also published several of Fritz Leiber's Fafhrd and the Gray Mouser stories, and added \"Sword and Sorcery\" to the cover in 1975.[16] In the same year a companion magazine, Sword & Sorcery Annual, was launched, but the first issue was the only one to appear.[8]\n" +
//                "\n" +
//                "The quality of the magazine remained high even as the financial stress was mounting in the late 1970s. White acquired cover artwork by Stephen Fabian and Douglas Beekman, and stories by some of the new generation of sf writers, such as George R. R. Martin and Charles Sheffield.[16] White departed in November 1978, but the first issue of Fantastic under Elinor Mavor's editorial control was April 1979. Because White had returned unsold stories she had very little to work with and was forced to fill the magazine with reprints. This led to renewed conflict with the sf community, which she did her best to defuse. At a convention in 1979 she met Harlan Ellison, who complained about the reprint policy; she explained that it was temporary and was able to get him to agree to contribute stories, publishing two pieces by him in Amazing over the next three years. The January 1980 issue of Fantastic (Mavor's fourth issue) was the last to contain reprinted stories.[20] Once the reprints had been phased out, Mavor was able to find new writers to work with, including Brad Linaweaver and John E. Stith, both of whom sold their first stories to Fantastic.[20] The last year of Fantastic showed \"a steady improvement in content\", according to Mike Ashley, who cites in particular Daemon, a serialized graphic story, illustrated by Stephen Fabian. However, at the end of 1980 Fantastic's independent existence ceased, and it was merged with Amazing.[20]";
//        str = str.replace('.', ' ').replace(',',' ').replace('\"', ' ');
//        StringTokenizer tokenizer = new StringTokenizer(str, " ");
//        int i = 0, count = 0;
//        ArrayList<String> already = new ArrayList<>();
//        long start = System.nanoTime();
//        while (tokenizer.hasMoreTokens())
//        {
//            i++;
//            String word = tokenizer.nextToken().toLowerCase();
//            boolean chk;
//            if(already.contains(word))
//            {
//
//                chk = true;
//            }
//            else {
//                chk = Neo4jObjectHelper.checkEntityAvailable(word);
//                    already.add(word);
//            }
//           //System.out.println(word + " >> "+chk);
//           if(chk) count++;
//        }
//        System.out.println((float) count/i*100);
//        System.out.print(System.nanoTime()-start);
//        Neo4jObjectHelper.close();
//
//        AddingExtending addingExtending = new AddingExtending();
//        //addingExtending.getPluralNotConnected();
//        addingExtending.reFind();
//        GraphDbCoveredRate rate = new GraphDbCoveredRate("testCovered.txt");
//        rate.start();
        ObjectController ob = new ObjectController();
        ob.linkingWords();
//        String str1 = "5a7444bc22f0cd3bcae5dd18";
//        String str2 = "5a7444bc22f0cd3bcae5dd18";
//        System.out.print(str1.compareTo(str2));

    }
    public static void cdd() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream("properties.txt");
        String template = "             case \"params\" :\n" +
                "                       helper(file, jsonObject, key, id, Neo4jQueryConnectionType);\n" +
                "                       break;\n";

        Scanner scanner = new Scanner(fis);
        String block = "";
        while (scanner.hasNextLine())
        {
            block+= template.replaceAll("\\bparams\\b",scanner.nextLine());
        }
        System.out.print(block);
    }
//        LogManager.getLogManager().reset();
////        FileInputStream fis = new FileInputStream(new File("listfile.txt"));
////        Scanner scanner = new Scanner(fis);
////        int i = 0;
////        while (scanner.hasNextLine())
////        {
////            i++;
////            scanner.nextLine();
////        }
////
////        ObjectController controller = new ObjectController();
////
////       controller.start(i);
//
//        Extending extending = new Extending();
//        extending.pluralVerbAdding();

//        Neo4JInteraction neo4JInteraction = new Neo4JInteraction("bolt://localhost:11002", "neo4j", "123456" );
//        List<Record> res = neo4JInteraction.execute("match (n {objectEntity: \"eee\"}) create (n)-[r:LANG_GRAMMAR_PLURAL]->(b:PLURAL{objectEntity:\"dscdcd\"}) return r");
//        System.out.println(res.size());


//         neo4JInteraction = new
//                Neo4JInteraction("bolt://localhost:7687", "neo4j", "123456");
//        FileInputStream fileInputStream = new FileInputStream(new File("not_complete.txt"));
//
//        Scanner scanner = new Scanner(fileInputStream);
//
//        while (scanner.hasNextLine()) {

//
//            String v1 = scanner.next().toLowerCase();
//            String v2 = scanner.next().toLowerCase();
//            String v3 = scanner.next().toLowerCase();
//
//            System.out.println(v1 + " " + v2 + " " + v3);
//
//            if (v1.equals(v2) && v1.equals(v3) && v2.equals(v3)) {
//                fileWriter.append(v1 + " " + v2 + " " + v3 + "\r\n");
//            } else {
//                StringTokenizer st1 = new StringTokenizer(v1, "/");
//                while (st1.hasMoreTokens()) {
//                    String verb1 = st1.nextToken();
//                    boolean chk1 = Neo4jObjectHelper.checkEntityAvailable(verb1);
//
////                    StringTokenizer st2 = new StringTokenizer(v2, "/");
////                    while (st2.hasMoreTokens()) {
////                        String verb2 = st2.nextToken();
////                        boolean chk2 = Neo4jObjectHelper.checkEntityAvailable(verb2);
////                        if (!chk1 || !chk2) {
////                            fileWriter.append(verb1 + "[" + chk1 + "]" + "!" + verb2 + "[" + chk2 + "]\r\n");
////                        } else {
////                            String query = "match(a:ObjectEntity{objectEntity :\"" + verb1 + "\"}), (b:ObjectEntity{objectEntity :\"" + verb2 + "\"}) set b:PAST_SIMPLE_VERB create (a)-[:LANG_GRAMMAR_PAST_SIMPLE_VERB]->(b)";
////                            neo4JInteraction.execute(query);
////                        }
////                    }
//                    StringTokenizer st3 = new StringTokenizer(v3, "/");
//                    while (st3.hasMoreTokens()) {
//                        String verb3 = st3.nextToken();
//                        boolean chk3 = Neo4jObjectHelper.checkEntityAvailable(verb3);
//                        if (!chk1 || !chk3) {
//                            fileWriter.append(verb1 + "[" + chk1 + "]" + "*" + verb3 + "[" + chk3 + "]\r\n");
//                        } else {
//                            String query = "match(a:ObjectEntity{objectEntity :\"" + verb1 + "\"}), (b:ObjectEntity{objectEntity :\"" + verb3 + "\"}) set b:PAST_PARTICIPLE_VERB create (a)-[:LANG_GRAMMAR_PAST_PARTICIPLE_VERB]->(b)";
//                            neo4JInteraction.execute(query);
//                        }
//                    }
//                }
//            }
//            String line = scanner.nextLine();
//            if (line.contains("!")) {
//
//               process(line,"!", true);
//            } else if (line.contains("*")) {
//                //V3
//                process(line,"*", false);
//            } else if (line.contains(" ")) {
//                StringTokenizer tokenizer = new StringTokenizer(line, " ");
//                String v = tokenizer.nextToken();
//                String query = "match (n:ObjectEntity{ objectEntity: \""+v+"\"}) set n:PAST_SIMPLE_VERB:PAST_PARTICIPLE_VERB create (n)-[:LANG_GRAMMAR_PAST_SIMPLE_VERB]->(n) create (n)-[:LANG_GRAMMAR_PAST_PARTICIPLE_VERB]->(n)";
//                neo4JInteraction.execute(query);
//            }
//        }
//
//        fileInputStream.close();
//        neo4JInteraction.close();
//    }

//    static void process(String line, String split, boolean v2) {
//        String pv1 = "", pv2 = "";
//        if (v2) {
//            pv1 = "PAST_SIMPLE_VERB";
//            pv2 = "LANG_GRAMMAR_PAST_SIMPLE_VERB";
//        } else {
//            pv1 = "PAST_PARTICIPLE_VERB";
//            pv2 = "LANG_GRAMMAR_PAST_PARTICIPLE_VERB";
//        }
//
//        String s1 = line.substring(0, line.indexOf(split));
//        boolean s1available = s1.contains("true");
//
//        s1 = s1.replaceAll("\\btrue\\b", "").
//                replaceAll("\\bfalse\\b", "").
//                replace('[', ' ').replace(']', ' ').trim();
//
//        String s2 = line.substring(line.indexOf(split) + 1, line.length());
//        boolean s2available = s2.contains("true");
//        s2 = s2.replaceAll("\\btrue\\b", "").
//                replaceAll("\\bfalse\\b", "").
//                replace('[', ' ').replace(']', ' ').trim();
//
//        if (s1available && !s2available) {
//            String query = "match(a:ObjectEntity{objectEntity :\"" + s1 + "\"}) create (b:ObjectEntity:"+pv1+" {objectEntity:\"" + s2 + "\"}) create (a)-[:"+pv2+"]->(b)";
//            neo4JInteraction.execute(query);
//        }
//        if (!s1available && s2available) {
//            String query = "match(a:ObjectEntity{objectEntity :\"" + s2 + "\"}) create (b:ObjectEntity:"+pv1+" {objectEntity:\"" + s1 + "\"}) create (b)-[:"+pv2+"]->(a)";
//            neo4JInteraction.execute(query);
//        }
//        if (!s1available && !s2available) {
//            String query = "create (a:ObjectEntity:"+pv1+" {objectEntity:\"" + s1 + "\"}) create (b:ObjectEntity:"+pv1+" {objectEntity:\"" + s2 + "\"}) create (a)-[:"+pv2+"]->(b)";
//            neo4JInteraction.execute(query);
//            System.out.println(query);
//        }
//
//    }
}
