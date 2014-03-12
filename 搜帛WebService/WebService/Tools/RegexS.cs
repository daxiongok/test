using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using System.Text.RegularExpressions;

namespace Tools
{
    public class RegexS
    {
        #region 正则表达式替换字符串
        /// <summary>
        /// 正则表达式替换字符串
        /// </summary>
        /// <param name="inputString">字符串内容</param>
        /// <param name="pattern">替换字符</param>
        /// <param name="replaceStr">替换值</param>
        /// <returns></returns>
        public static string RegexReplace(string inputString, string pattern, string replaceStr)
        {
            try
            {
                return Regex.Replace(inputString, pattern, replaceStr);
            }
            catch (Exception e)
            {
                return e.Message;
            }
        }
        #endregion

        #region 判断字符串是否为正整数
        /// <summary>
        /// 判断字符串是否为正整数
        /// </summary>
        /// <param name="objString">要匹配的字符串</param>
        /// <returns>返回真假值，true：匹配；false：不匹配</returns>
        public static bool IsInt(String objString)
        {
            Regex myReg = new Regex(@"^\d+$");
            return myReg.IsMatch(objString);
        }
        #endregion

        #region 判断输入的字符串是否全是英文（不区分大小写）
        /// <summary>
        /// 判断输入的字符串是否全是英文（不区分大小写）
        /// </summary>
        /// <param name="objString">所要匹配的字符串</param>
        /// <returns>返回真假值，true：匹配；false：不匹配</returns>
        public static bool isEnglishString(String objString)
        {
            Regex myReg = new Regex(@"^[a-zA-Z]+$");
            return myReg.IsMatch(objString);
        }
        #endregion


        /// <summary>
        /// 返回字符串中的数字
        /// </summary>
        /// <param name="objString"></param>
        /// <returns></returns>
        public static string RunNumber(string objString)
        {
            return Regex.Match(objString, "[0-9]+").Value.ToString();
        }

        /// <summary>
        /// 返回字符串中左边的字符
        /// </summary>
        /// <param name="objString"></param>
        /// <returns></returns>
        public static string RunLeftString(string objString)
        {
            return Regex.Match(objString, "[%*/+ -.A-Za-z]+").Value.ToString();
        }
        /// <summary>
        /// 返回字符串中右边的字符
        /// </summary>
        /// <param name="objString"></param>
        /// <returns></returns>
        public static string RunRightString(string objString)
        {
            return Regex.Match(objString, "[%*/+ -.A-Za-z]+$").Value.ToString();
        }

        /// <summary>
        /// 返回字符串中的字符
        /// </summary>
        /// <param name="objString"></param>
        /// <returns></returns>
        public static string RunString(string objString)
        {
            return Regex.Match(objString, "[A-Za-z]+").Value.ToString();
        }


        #region 判断所输入的字符串是否为中文
        /// <summary>
        /// 判断所输入的字符串是否为中文
        /// </summary>
        /// <param name="objString">所要匹配的字符串</param>
        /// <returns>返回真假值，true：匹配；false：不匹配</returns>
        public static bool isChinese(String objString)
        {
            Regex myReg = new Regex(@"^[\u4e00-\u9fa5]+$");
            return myReg.IsMatch(objString);
        }
        #endregion

        #region 判断输入字符串是否为英文及数字（英文不区分大小写）
        /// <summary>
        /// 判断输入字符串是否为英文及数字（英文不区分大小写）
        /// </summary>
        /// <param name="objString">所要匹配的字符串</param>
        /// <returns>返回真假值，true：匹配；false：不匹配</returns>
        public static bool isEngNum(String objString)
        {
            Regex myReg = new Regex(@"^[*/+-a-zA-Z0-9]+$");
            return myReg.IsMatch(objString);
        }
        #endregion

        #region 判断输入字符串是否为英文A－D及数字（英文限制在A－D之间英文不区分大小写）
        /// <summary>
        /// 判断输入字符串是否为英文A－D及数字（英文限制在A－D之间英文不区分大小写）
        /// </summary>
        /// <param name="objString">所要匹配的字符串</param>
        /// <returns>返回真假值，true：匹配；false：不匹配</returns>
        public static bool isEngNumMax(String objString)
        {
            Regex myReg = new Regex(@"^[a-dA-D0-9]+$");
            return myReg.IsMatch(objString);
        }
        #endregion

        #region  判断是否为英文及数字组合
        /// <summary>
        /// 判断是否为英文及数字组合
        /// </summary>
        /// <param name="objString"></param>
        /// <returns></returns>
        public static bool InEngNum(string objString)
        {
            //Regex myReg = new Regex(@"^(?![0-9]+$)[a-zA-Z0-9]{1,25}$");           
            //return myReg.IsMatch(objString);"^[a-zA-Z]\w{5,17}$"
            return Regex.IsMatch(objString, @"^[*/+-a-zA-Z0-9]{1,20}$");
        }
        #endregion

        #region 判断输入字符串是否为英文，数字，中文（英文不区分大小写）
        /// <summary>
        /// 判断输入字符串是否为英文，数字，中文（英文不区分大小写）
        /// </summary>
        /// <param name="objString">所要匹配的字符串</param>
        /// <returns>返回真假值，true：匹配；false：不匹配</returns>
        public static bool isChineseEngNum(String objString)
        {
            Regex myReg = new Regex(@"^[\u4e00-\u9fa5a-zA-Z0-9]+$");
            return myReg.IsMatch(objString);
        }
        #endregion

        #region 判断输入字符串是否为小数
        /// <summary>
        /// 判断输入字符串是否为小数
        /// </summary>
        /// <param name="objString">所要匹配的字符串</param>
        /// <returns>返回真假值，true：匹配；false：不匹配</returns>
        public static bool isFloat(String objString)
        {
            Regex myReg = new Regex(@"^[0-9]+[.][0-9]+|[0-9]+$");
            return myReg.IsMatch(objString);
        }
        #endregion

        #region 判断日期格式是否有效
        /// <summary>
        /// 判断日期格式是否有效
        /// </summary>
        /// <param name="objString"></param>
        /// <returns></returns>
        public static bool IsDate(String objString)
        {
            Regex myReg = new Regex(@"\b(?<year>\d{2,4})-(?<month>\d{1,2})-(?<day>\d{1,2})\b");
            return myReg.IsMatch(objString);
        }
        #endregion

        #region 判断字符串是否符合此正则表达试
        /// <summary>
        /// 判断字符串是否符合此正则表达试
        /// </summary>
        /// <param name="str">所要匹配的字符串</param>
        /// <param name="regString">正则字符串（如：^[1-9]{1}$）</param>
        /// <returns>返回真假值，true：匹配；false：不匹配</returns>
        public static bool IsFitStrings(String str, String regString)
        {
            Regex objPattern = new Regex(regString);
            bool returnValue = objPattern.IsMatch(str);
            return returnValue;
        }
        #endregion

        #region 判断字符串是否为手机号或小灵通号
        /// <summary>
        /// 判断字符串是否为手机号或小灵通号
        /// </summary>
        /// <param name="telNumber">所要匹配的字符串</param>
        /// <returns>返回真假值，true：匹配；false：不匹配</returns>
        public static bool IsMobile(string telNumber)
        {
            if (telNumber == "")
                return false;
            Regex myReg = new Regex(@"^((\d{11,12})|(\d{7}))$");
            return myReg.IsMatch(telNumber);
        }
        #endregion

        #region 判断字符串是否为Email地址
        /// <summary>
        /// 判断字符串是否为Email地址
        /// </summary>
        /// <param name="email">所要匹配的字符串</param>
        /// <returns>返回真假值，true：匹配；false：不匹配</returns>
        public static bool IsEmail(string email)
        {
            if (email == "")
            {
                return false;
            }
            Regex myReg = new Regex(@"^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$");
            return myReg.IsMatch(email);
        }
        #endregion

        #region 判断字符串是否为座机(如xxx-xxxxxxx-xxx)
        /// <summary>
        /// 判断字符串是否为座机(如xxx-xxxxxxx-xxx)
        /// </summary>
        /// <param name="tel">所要匹配的字符串</param>
        /// <returns>返回真假值，true：匹配；false：不匹配</returns>
        public static bool IsTel(string tel)
        {
            if (tel == "")
                return false;
            Regex myReg = new Regex(@"^(\(\d{3,4}\)|\d{3,4}-)?\d{7,8}(-\d{1,5})?$");
            return myReg.IsMatch(tel);
        }
        #endregion

        #region 判断是否为邮政编码
        /// <summary>
        /// 判断是否为邮政编码
        /// </summary>
        /// <param name="Zip"></param>
        /// <returns></returns>
        public static bool IsValidZip(string Zip)
        {
            return Regex.IsMatch(Zip, @"^[a-z0-9 ]{3,12}$");
        }
        #endregion

        #region  判断是否为有效身份证号
        /// <summary>
        /// 判断是否为有效身份证号
        /// </summary>
        /// <param name="IdCard"></param>
        /// <returns></returns>
        public static bool IsIdCard(string IdCard)
        {
            return Regex.IsMatch(IdCard, @"^\d{15}|\d{18}$");
        }
        #endregion

        #region 返回分割字符串
        /// <summary>
        /// 返回分割字符串
        /// </summary>
        /// <param name="Str">要分割的字符串集</param>
        /// <param name="spliststr">指定分割字符</param>
        /// <returns></returns>
        public static string FindStr(string Str, string spliststr)
        {
            string[] str2 = System.Text.RegularExpressions.Regex.Split(Str, @"[" + spliststr + "]+");
            foreach (string i in str2)
            {
                return i.ToString();
            }
            return "";
        }
        #endregion
    }
}
