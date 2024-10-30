#region XD World Recipe V 3
// FileName: ProviderPMStatistic.cs
// Author: Dexter Zafra
// Date Created: 3/8/2009
// Website: www.ex-designz.net
#endregion
using System;
using System.Data;
using XDRecipe.BL;
using XDRecipe.Common;
using XDRecipe.Model;

namespace XDRecipe.BL.Providers.PrivateMessages
{
    /// <summary>
    /// Returns Private Message Statistic Counter
    /// </summary>
    public sealed class ProviderPMStatistic : BasePMObj
    {
        public ProviderPMStatistic()
        {
        }

        public override void FillUp()
        {
            IDataReader dr = Blogic.ActionProcedureDataProvider.GetPrivateMessageStatistic;

            while (dr.Read())
            {
                if (dr["TotalPMCount"] != DBNull.Value)
                {
                    this._TotalPMCount = (int)dr["TotalPMCount"];
                }
                if (dr["SentTodayCount"] != DBNull.Value)
                {
                    this._CountSentPMToday = (int)dr["SentTodayCount"];
                }
            }

            dr.Close();
        }
    }
}
