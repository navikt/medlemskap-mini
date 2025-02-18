# language: no
# encoding: UTF-8

Egenskap: Mapping av dekning i Medlemskap

  Scenariomal: Bruker har dekning fra Medl
    Gitt følgende om dekning fra MedlMedlemskapsunntak
      | Dekning    |
      | <Dekning>  |

    Når medlemskapsuntak mappes

    Så skal mappet dekning i Medlemskap være
      | MedlemskapDekning    |
      | <MedlemskapDekning>|

    Eksempler:
      | Dekning              | MedlemskapDekning   |
      | FTL_2-6              | FTL_2-6             |
      | FTL_2-7a_2_ledd_a    | FTL_2-7a_2_ledd_a   |
      | FTL_2-7a_2_ledd_b    | FTL_2-7a_2_ledd_b   |
      | FTL_2-7_bok_a        | FTL_2-7_bok_a       |
      | FTL_2-7_bok_b        | FTL_2-7_bok_b       |
      | FTL_2-7_3_ledd_a     | FTL_2-7_3_ledd_a    |
      | FTL_2-7_3_ledd_b     | FTL_2-7_3_ledd_b    |
      | FTL_2-9_a            | FTL_2-9_a           |
      | FTL_2-9_b            | FTL_2-9_b           |
      | FTL_2-9_c            | FTL_2-9_c           |
      | FTL_2-9_1_ledd_a     | FTL_2-9_1_ledd_a    |
      | FTL_2-9_1_ledd_b     | FTL_2-9_1_ledd_b    |
      | FTL_2-9_1_ledd_c     | FTL_2-9_1_ledd_c    |
      | FTL_2-9_2_ld_jfr_1a  | FTL_2-9_2_ld_jfr_1a |
      | FTL_2-9_2_ld_jfr_1c  | FTL_2-9_2_ld_jfr_1c |
      | FTL_2-9_2_ledd       | FTL_2-9_2_ledd      |
      | Full                 | Full                |
      | IHT_Avtale           | IHT_Avtale          |
      | IHT_Avtale_Forord    | IHT_Avtale_Forord   |
      | IKKEPENDEL           | IKKEPENDEL          |
      | IT_DUMMY             | IT_DUMMY            |
      | IT_DUMMY_EOS         | IT_DUMMY_EOS        |
      | Opphor               | Opphor              |
      | PENDEL               | PENDEL              |
      | Unntatt              | Unntatt             |

